package com.example.beautysalonRESTAPI.backend.model;

import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "", schema="beautysalon")
public class ClientHistory {
    
    @Column(name ="id_historikut")
    private int id_historiku;

    @Column(name = "ID")
    private int id_personit; 

    @Column(name ="Sherbimi")
    private int id_sherbimit;

    @Column(name = "id_atributit")
    private int id_atributit;

    @Column(name = "emri_sherbimit")
    private String emri_sherbimit;

    @Column(name = "opsioni")
    private String emri_atributit;

    @Column(name = "data_sherbimit")
    private Timestamp data_sherbimit;

    @Column(name = "pagesa")
    private Double pagesa;

    @Column(name = "qmimiBazik")
    private Double qmimiBazik;

    @Column(name = "zbritja")
    private int zbritja;

    @Column(name = "pershkrimi")
    private String pershkrimi;

    @Column(name = "kohezgjatja")
    private Time kohezgjatja;


}
