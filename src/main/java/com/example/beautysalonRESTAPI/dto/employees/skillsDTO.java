package com.example.beautysalonRESTAPI.dto.employees;

import java.util.List;
import java.util.stream.Collectors;

import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.skills;

public class skillsDTO {

    private Long id;
    private Long id_employee;
    private Long id_service;
    private Sherbimet service;

    public skillsDTO(skills skill) {
        this.id = skill.getId();
        this.id_employee = skill.getEmployees().getID();
        this.id_service = skill.getSherbimet().getID();
        this.service = skill.getSherbimet();
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
}