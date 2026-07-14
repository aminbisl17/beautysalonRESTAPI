package com.example.beautysalonRESTAPI.repository.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.model.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    Optional<Employees> findByUsername(String username);

    @Query("SELECT e FROM Employees e WHERE e.ID = :id")
    Optional<Employees> findEmployeeById(@Param("id") Long id);

    @Query("SELECT e FROM Employees e WHERE e.is_active = true")
List<Employees> findAllActiveEmployees();
}
