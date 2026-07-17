package com.example.beautysalonRESTAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="skills")
public class skills {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID") 
    @JsonIgnore
    private Employees employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID") 
    @JsonIgnore
    private Sherbimet sherbimet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Sherbimet getSherbimet() {
        return sherbimet;
    }

    public void setSherbimet(Sherbimet sherbimet) {
        this.sherbimet = sherbimet;
    }
}
