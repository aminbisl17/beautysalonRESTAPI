package com.example.beautysalonRESTAPI.repository.Terminet;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

  
    @Query("""
    SELECT t.data_caktimit
    FROM Terminet t
    WHERE t.employee.ID = :employeeId
    """)
List<LocalDateTime> findDataCaktimitByEmployeeId(
    @Param("employeeId") Long employeeId
);

 
@Query("""
    SELECT t.data_caktimit, COALESCE(SUM(d.kohezgjatja), 0)
    FROM Terminet t
    LEFT JOIN t.detajet_termineve d
    WHERE t.employee.ID = :employeeId
    GROUP BY t.id_terminit, t.data_caktimit
    """)
List<Object[]> findDataCaktimitAndDuration(@Param("employeeId") Long employeeId);


/* 

@Query("""
    SELECT t.data_caktimit, COALESCE(SUM(d.kohezgjatja), 0)
    FROM Terminet t
    LEFT JOIN t.detajet_termineve d
    WHERE t.employee.ID = :employeeId
      AND t.data_caktimit >= :from
    GROUP BY t.id_terminit, t.data_caktimit
    """)
List<Object[]> findDataCaktimitAndDuration(@Param("employeeId") Long employeeId,
                                           @Param("from") LocalDateTime from);
                                           
                                        */
}
