package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="atributet_sherbimeve", schema="beautysalon")
public class Atributet_sherbimeve {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_atributit;

    @ManyToOne
    @JoinColumn(name="ID")  // FK referencing sherbimet.ID
    private Sherbimet sherbimi;

    @Column(name="opsioni")
    private String opsioni;

    @Column(name="pershkrimi_opsionit")
    private String pershkrimi;

    @Column(name="kohezgjatja")
    private LocalTime kohezgjatja;

    @Column(name="qmimi")
    private Double qmimi;

    @Column(name="zbritja")
    private int zbritja;

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

    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }

    public Double getQmimi() {
        return qmimi;
    }

    public int getZbritja() {
        return zbritja;
    }
}