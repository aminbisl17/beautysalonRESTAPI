package com.example.beautysalonRESTAPI.repository.Terminet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beautysalonRESTAPI.model.Terminet;

public interface TerminetRepository extends JpaRepository<Terminet, Long> {
    
    @Query(value = """
        SELECT DISTINCT t
        FROM Terminet t
        LEFT JOIN FETCH t.client
        LEFT JOIN FETCH t.employee
        LEFT JOIN FETCH t.detajet_termineve d
        LEFT JOIN FETCH d.sherbimet
        LEFT JOIN FETCH d.atributet_sherbimeve
        WHERE t.employee.ID = :employeeId
        """)
    List<Terminet> findByEmployeeId(@Param("employeeId") Long employeeId);
}
