package com.example.beautysalonRESTAPI.repository.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.model.employeeAvailability;
import java.util.List;
import java.util.Optional;


@Repository
public interface employeeAvailabilityRepository extends JpaRepository<employeeAvailability,Long>{

        Optional<employeeAvailability> findByIdAvailability(Long idAvailability);

 
    List<employeeAvailability> findByEmployees_ID(Long id);
}
