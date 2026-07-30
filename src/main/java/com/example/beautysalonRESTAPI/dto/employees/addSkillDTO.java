package com.example.beautysalonRESTAPI.dto.employees;

import java.util.List;

public class addSkillDTO {
        private Long id_employee;
    private List<Long> id_services;

    public Long getId_employee() {
        return id_employee;
    }

    public void setId_employee(Long id_employee) {
        this.id_employee = id_employee;
    }

    public List<Long> getId_services() {
        return id_services;
    }

    public void setId_services(List<Long> id_services) {
        this.id_services = id_services;
    }
}
