package com.example.beautysalonRESTAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="atributet_sherbimeve", schema="dbo")
public class Atributet_sherbimeve {
  
@Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id_atributit") 
 private Long id_atributit;

    @ManyToOne
    @JoinColumn(name="ID") 
    @JsonIgnore
    private Sherbimet sherbimi;

    @Column(name="opsioni")
    private String opsioni;

    @Column(name="pershkrimi_opsionit")
    private String pershkrimi;

    @Column(name="kohezgjatja")
    private int kohezgjatja;

    @Column(name="qmimi")
    private Double qmimi;

    @Column(name="zbritja")
    private int zbritja;

    public void setId_atributit(Long id_atributit) {
        this.id_atributit = id_atributit;
    }

    public void setSherbimi(Sherbimet sherbimi) {
        this.sherbimi = sherbimi;
    }

    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    public void setQmimi(Double qmimi) {
        this.qmimi = qmimi;
    }

    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }

    public Long getId_atributit() {
        return id_atributit;
    }

    public Sherbimet getSherbimi() {
        return sherbimi;
    }

    public String getOpsioni() {
        return opsioni;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public int getKohezgjatja() {
        return kohezgjatja;
    }

    public Double getQmimi() {
        return qmimi;
    }

    public int getZbritja() {
        return zbritja;
    }
}