package com.example.beautysalonRESTAPI.backend.security.Responses;

import java.time.LocalDateTime;

import com.example.beautysalonRESTAPI.backend.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.backend.model.Employees;

public class EmployeeAuthResponse {

    private String token;
  //  private String role;

    private EmployeesDTO empdto;

    public EmployeeAuthResponse(Employees employee, String token, String role) {
    
        this.empdto = new EmployeesDTO(employee);
        this.token = token;
       // this.role = role;
    }


    
    public EmployeesDTO getEmpdto() {
        return empdto;
    }

    public void setEmpdto(EmployeesDTO empdto) {
        this.empdto = empdto;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

   // public String getRole() { return role; }
 //   public void setRole(String role) { this.role = role; }
}
