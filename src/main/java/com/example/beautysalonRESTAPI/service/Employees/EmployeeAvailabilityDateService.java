package com.example.beautysalonRESTAPI.service.Employees;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates.AvailabilityDetails;
import com.example.beautysalonRESTAPI.dto.terminet.DetajetTermineveDTO;
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

    try {
       
        jdbcTemplate.execute(
    connection -> {
        CallableStatement cs = connection.prepareCall(
            "{call dbo.setAvailableDates(?, ?, ?, ?)}"
        );

        cs.setLong(1, d.getId_employee());
        cs.setDate(2, Date.valueOf(d.getStart_date()));
        cs.setDate(3, Date.valueOf(d.getEnd_date()));
        cs.setObject(4, tvp);

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
public boolean updateAvailableEmployeeDates(Long availabilityId, Long employeeIdFromToken, AvailableEmployeeDates updatedData) {
    // 1. Fetch the target record along with its nested relationship check
    Optional<employeeAvailability> existingRecordOpt = repo.findById(availabilityId);
    
    if (existingRecordOpt.isEmpty()) {
        return false;
    }

    employeeAvailability existingRecord = existingRecordOpt.get();

    // 2. Security validation check
    if (existingRecord.getEmployees() == null || existingRecord.getEmployees().getID() != employeeIdFromToken) {
        return false; // Unauthorized update attempt
    }

    // 3. Update top-level values conditionally (PATCH behavior)
    if (updatedData.getStart_date() != null) {
        existingRecord.setStart_date(updatedData.getStart_date());
    }
    if (updatedData.getEnd_date() != null) {
        existingRecord.setEnd_date(updatedData.getEnd_date());
    }

    // 4. Update the child grid details collection if provided
    if (updatedData.getAvailabilityDetails() != null) {
        // Clear old list to handle changes, inserts, or removals perfectly
        existingRecord.getAvailabilityDetails().clear();

        // Convert DTO details to your actual database entity model elements
        for (AvailabilityDetails detailDto : updatedData.getAvailabilityDetails()) {
            // Assuming you have an entity structure matching AvailabilityDetails
            // Create a new entity instance here, attach it to existingRecord, and add to collection
            
            /* Example matching your structure:
            EmployeeAvailabilityDetail newDetail = new EmployeeAvailabilityDetail();
            newDetail.setDayOfWeek(detailDto.getDay_of_week());
            newDetail.setStartTime(detailDto.getStart_time());
            newDetail.setEndTime(detailDto.getEnd_time());
            newDetail.setPauseStart(detailDto.getPause_start());
            newDetail.setPauseEnd(detailDto.getPause_end());
            newDetail.setEmployeeAvailability(existingRecord);
            
            existingRecord.getAvailabilityDetails().add(newDetail);
            */
        }
    }

    // 5. Commit state updates back to the repository layers
    repo.save(existingRecord);
    return true;
}
}
