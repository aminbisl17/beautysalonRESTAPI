package com.example.beautysalonRESTAPI.dto;

public class DashboardDTO {
    
    Long clients, employees, services;

    public Long getClients() {
        return clients;
    }

    public void setClients(Long clients) {
        this.clients = clients;
    }

    public Long getEmployees() {
        return employees;
    }

    public void setEmployees(Long employees) {
        this.employees = employees;
    }

    public Long getServices() {
        return services;
    }

    public void setServices(Long services) {
        this.services = services;
    }
}
