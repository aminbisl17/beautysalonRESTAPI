package com.example.beautysalonRESTAPI.service;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates.AvailabilityDetails;
import com.example.beautysalonRESTAPI.dto.terminet.DetajetTermineveDTO;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Service
public class EmployeeAvailabilityDateService {
    
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
}
