package com.example.beautysalonRESTAPI.backend.model;


import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="sherbimet", schema="beautysalon")

public class Sherbimet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;

    @Column(name="emri_sherbimit")
    private String emri_sherbimit;

    @Column(name="pershkrimi")
    private String pershkrimi;

    @Column(name="qmimi_baze")
    private Double qmimi_baze;

    @Column(name="is_active")
    private boolean is_active;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @Column(name="zbritja")
    private int zbritja;
    
    @Column(name="kohezgjatja")
    private LocalTime kohezgjatja;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }

    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Double getQmimi_baze() {
        return qmimi_baze;
    }

    public void setQmimi_baze(Double qmimi_baze) {
        this.qmimi_baze = qmimi_baze;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getZbritja() {
        return zbritja;
    }

    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }

    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
