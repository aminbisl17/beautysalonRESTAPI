package com.example.beautysalonRESTAPI.service.Employees;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates.AvailabilityDetails;
import com.example.beautysalonRESTAPI.dto.terminet.DetajetTermineveDTO;
import com.example.beautysalonRESTAPI.model.availabilityDetails;
import com.example.beautysalonRESTAPI.model.employeeAvailability;
import com.example.beautysalonRESTAPI.repository.Employee.employeeAvailabilityRepository;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import jakarta.transaction.Transactional;

@Service
public class EmployeeAvailabilityDateService {
    
    @Autowired 
    private employeeAvailabilityRepository repo;

        @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean setAvailableEmployeeDates(AvailableEmployeeDates d) throws SQLServerException {

    SQLServerDataTable tvp = new SQLServerDataTable();

    tvp.addColumnMetadata("day_of_week", Types.INTEGER);
    tvp.addColumnMetadata("start_time", Types.TIME);
    tvp.addColumnMetadata("end_time", Types.TIME);
    tvp.addColumnMetadata("pause_start", Types.TIME);
    tvp.addColumnMetadata("pause_end", Types.TIME);

    for (AvailabilityDetails a : d.getAvailabilityDetails()) {
        tvp.addRow(
            a.getDay_of_week(),
            a.getStart_time(),
            a.getEnd_time(),
            a.getPause_start(),
            a.getPause_end()
        );
    }

       SQLServerDataTable tvpSkills = new SQLServerDataTable();

    tvpSkills.addColumnMetadata("id_skills", Types.BIGINT);

    for (Long skillId : d.getAvailableSkills()) {
        tvpSkills.addRow(skillId);
    }


    try {
       
        jdbcTemplate.execute(
    connection -> {
        CallableStatement cs = connection.prepareCall(
            "{call dbo.setAvailableDates(?, ?, ?, ?, ?)}"
        );

        cs.setLong(1, d.getId_employee());
        cs.setDate(2, Date.valueOf(d.getStart_date()));
        cs.setDate(3, Date.valueOf(d.getEnd_date()));
        cs.setObject(4, tvp);
        cs.setObject(5, tvpSkills);

        return cs;
    },
    (CallableStatement cs) -> {
        cs.execute();
        return null;
    }
);
        return true;

    } catch (DataAccessException ex) {
        throw new RuntimeException(
            ex.getMostSpecificCause().getMessage()
        );
    }
}

@Transactional
public int deleteMultipleAvailableEmployeeDates(List<Long> availabilityIds, Long employeeIdFromToken) {
    // 1. Fetch only the records that exist AND belong to this specific employee
    List<employeeAvailability> recordsToDelete = repo.findAllById(availabilityIds)
            .stream()
            .filter(avail -> avail.getEmployees() != null && avail.getEmployees().getID() == employeeIdFromToken)
            .toList();

    // 2. If valid records match, perform a batch deletion
    if (!recordsToDelete.isEmpty()) {
        repo.deleteAllInBatch(recordsToDelete);
        return recordsToDelete.size(); // Returns how many rows were actually removed
    }

    return 0; 
}

@Transactional
public boolean deleteAvailableEmployeeDate(Long availabilityId, Long employeeIdFromToken) {

    Optional<employeeAvailability> record = repo.findByIdAvailability(availabilityId);

    if (record.isEmpty()) {
        return false;
    }

    employeeAvailability availability = record.get();

    if (availability.getEmployees().getID() != employeeIdFromToken.longValue()) {
        return false;
    }

    repo.delete(availability);
    return true;
}

@Transactional
public boolean updateAvailableEmployeeDates(Long availabilityId, Long employeeIdFromToken, AvailableEmployeeDates updatedData) {

    Optional<employeeAvailability> existingRecordOpt = repo.findById(availabilityId);
    
    if (existingRecordOpt.isEmpty()) {
        return false;
    }

    employeeAvailability existingRecord = existingRecordOpt.get();

    if (existingRecord.getEmployees() == null || existingRecord.getEmployees().getID() != employeeIdFromToken) {
        return false; // Unauthorized update attempt
    }

  if (updatedData.getAvailabilityDetails() != null) {

    Map<Integer, availabilityDetails> existing =
        existingRecord.getAvailabilityDetails()
            .stream()
            .collect(Collectors.toMap(
                availabilityDetails::getDay_of_week,
                Function.identity()
            ));

    for (AvailableEmployeeDates.AvailabilityDetails dto : updatedData.getAvailabilityDetails()) {

        availabilityDetails entity = existing.get(dto.getDay_of_week());

        if (entity == null) {
            continue;
        }

        if (dto.getStart_time() != null && dto.getEnd_time() != null) {
    if (!dto.getEnd_time().isAfter(dto.getStart_time())) {
        throw new IllegalArgumentException("End time must be after start time");
    }
}
        entity.setStart_time(dto.getStart_time());
        entity.setEnd_time(dto.getEnd_time());
        entity.setPause_start(dto.getPause_start());
        entity.setPause_end(dto.getPause_end());
    }
}

    repo.save(existingRecord);
    return true;
}
}
