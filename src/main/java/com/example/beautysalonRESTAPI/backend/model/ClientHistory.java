package com.example.beautysalonRESTAPI.backend.model;

import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "historiku", schema="beautysalon")
public class ClientHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "ID")
    private Long id_personit; 

    @Column(name ="id_historikut")
    private int id_historiku;


    @Column(name ="Sherbimi")
    private int id_sherbimit;

    @Column(name = "id_atributit")
    private int id_atributit;

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
