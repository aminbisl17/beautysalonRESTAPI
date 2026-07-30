package com.example.beautysalonRESTAPI.dto.employees;

import java.util.List;
import java.util.stream.Collectors;

import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.skills;

public class skillsDTO {

    private Long id;
    private Long id_employee;
  private List<Long> id_services;
    private List<Sherbimet> services;

    public skillsDTO() {}

    public skillsDTO(List<skills> skillsList) {

        if (!skillsList.isEmpty()) {
            this.id = skillsList.get(0).getId();
            this.id_employee = skillsList.get(0).getEmployees().getID();

            this.services = skillsList.stream()
                    .map(skills::getSherbimet)
                    .collect(Collectors.toList());
        }
    }

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Sherbimet> getServices() {
        return services;
    }

    public void setServices(List<Sherbimet> services) {
        this.services = services;
    }
}