package com.example.beautysalonRESTAPI.service.Employees;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.repository.EmployeesRepository;

@Service
public class EmployeeService {
    
    EmployeesRepository employeeRepo;

    public EmployeeService(EmployeesRepository employeeRepo){
            this.employeeRepo = employeeRepo;
    }
}
