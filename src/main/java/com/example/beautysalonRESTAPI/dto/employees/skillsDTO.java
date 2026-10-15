
package com.example.beautysalonRESTAPI.dto.employees;

import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.skills;

public class skillsDTO {

    private Long id;
    private Long id_employee;
    private Long id_service;
    private Sherbimet service;

    public skillsDTO(skills skill) {
        this.id = skill.getId();

        if (skill.getEmployees() != null) {
            this.id_employee = skill.getEmployees().getID();
        }

        Sherbimet sherbimet = skill.getSherbimet();

        if (sherbimet != null) {
            this.id_service = sherbimet.getID();
            this.service = sherbimet;
        }
    }

    public Long getId() {
        return id;
    }

    public Long getId_employee() {
        return id_employee;
    }

    public Long getId_service() {
        return id_service;
    }

    public Sherbimet getService() {
        return service;
    }

    public void setId_employee(Long id_employee) {
        this.id_employee = id_employee;
    }

    public void setId_service(Long id_service) {
        this.id_service = id_service;
    }

    public void setService(Sherbimet service) {
        this.service = service;
    }
}
