package com.example.beautysalonRESTAPI.backend.repository.employees;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.employees;

@Repository
public interface EmployeesRepository extends JpaRepository<employees, Long> {
    Optional<employees> findByUsername(String username);
}
