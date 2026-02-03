package com.example.beautysalonRESTAPI.backend.service.terminet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import com.example.beautysalonRESTAPI.backend.dto.terminet.DetajetTermineveDTO;
import com.example.beautysalonRESTAPI.backend.dto.terminet.TerminetCreateDTO;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TerminetService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createAppointment(TerminetCreateDTO dto) throws SQLException {

    

        SQLServerDataTable tvp = new SQLServerDataTable();
        tvp.addColumnMetadata("ID_sherbimit", Types.INTEGER);
        tvp.addColumnMetadata("id_atributit", Types.INTEGER);
        tvp.addColumnMetadata("kohezgjatja", Types.TIME);
        tvp.addColumnMetadata("pagesa", Types.DECIMAL);

        for (DetajetTermineveDTO d : dto.getDetajetTermineve()) {
            tvp.addRow(
                d.getSherbimetId(),
                d.getAtributetId(),
                d.getKohezgjatja(),
                d.getPagesa()
            );
        }

        // Call stored procedure
        return jdbcTemplate.execute(
            connection -> {
                CallableStatement cs = connection.prepareCall(
                    "{call create_appointment(?, ?, ?, ?, ?, ?)}"
                );
                cs.setLong(1, dto.getClientId());
                cs.setLong(2, dto.getEmployeeId());
                cs.setString(3, dto.getPershkrimi());
                cs.setTimestamp(4, Timestamp.valueOf(dto.getDataCaktimit()));
                cs.setObject(5, tvp); // table-valued param
                cs.registerOutParameter(6, Types.BIT); // @status OUTPUT
                return cs;
            },
            (CallableStatement cs) -> {
                cs.execute();
                return cs.getBoolean(6);
            }
        );
         
    }
}
