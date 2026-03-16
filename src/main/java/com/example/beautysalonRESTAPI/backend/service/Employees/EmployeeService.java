package com.example.beautysalonRESTAPI.backend.service.Employees;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;

@Service
public class EmployeeService {
    
    EmployeesRepository employeeRepo;

    public EmployeeService(EmployeesRepository employeeRepo){
            this.employeeRepo = employeeRepo;
    }
}
