package com.example.beautysalonRESTAPI.backend.security.Responses;

import com.example.beautysalonRESTAPI.backend.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.backend.model.Employees;

public class EmployeeAuthResponse {

    private String token;

    private EmployeesDTO empdto;

    public EmployeeAuthResponse(Employees employee, String token) {
    
        this.empdto = new EmployeesDTO(employee);
        this.token = token;
    }


    
    public EmployeesDTO getEmpdto() {
        return empdto;
    }

    public void setEmpdto(EmployeesDTO empdto) {
        this.empdto = empdto;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
