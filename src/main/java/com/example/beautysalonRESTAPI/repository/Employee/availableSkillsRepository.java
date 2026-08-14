package com.example.beautysalonRESTAPI.repository.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.availableSkills;

@Repository
public interface availableSkillsRepository extends JpaRepository<availableSkills, Long> {
    @Query(value = """
    SELECT DISTINCT s.*
    FROM availableSkill avs
    JOIN skills sk ON sk.id = avs.id_skills
    JOIN sherbimet s ON s.ID = sk.id_service
    JOIN employeeAvailability ea
        ON ea.id_availability = avs.id_availability
    WHERE ea.id_employee = :employeeId
    """, nativeQuery = true)
List<Sherbimet> findServicesByEmployeeId(@Param("employeeId") Long employeeId);
}
