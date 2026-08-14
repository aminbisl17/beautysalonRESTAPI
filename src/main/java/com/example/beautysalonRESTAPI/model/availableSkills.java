package com.example.beautysalonRESTAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "availableSkill")
public class availableSkills {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_availability")
    @JsonIgnore
    private employeeAvailability empAva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_skills")
    @JsonIgnore
    private skills skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public employeeAvailability getEmpAva() {
        return empAva;
    }

    public void setEmpAva(employeeAvailability empAva) {
        this.empAva = empAva;
    }

    public skills getSkills() {
        return skills;
    }

    public void setSkills(skills skills) {
        this.skills = skills;
    }
}
