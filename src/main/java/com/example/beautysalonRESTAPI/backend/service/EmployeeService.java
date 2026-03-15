package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.employees.EmployeesRepository;

@Service
public class EmployeeService {
    
    EmployeesRepository employeeRepo;

    public EmployeeService(EmployeesRepository employeeRepo){
            this.employeeRepo = employeeRepo;
    }
}
