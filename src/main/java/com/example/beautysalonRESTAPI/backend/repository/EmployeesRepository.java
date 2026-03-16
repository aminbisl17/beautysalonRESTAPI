package com.example.beautysalonRESTAPI.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    Optional<Employees> findByUsername(String username);

    @Query("SELECT e FROM Employees e WHERE e.ID = :id")
    Optional<Employees> findEmployeeById(@Param("id") Long id);
}
