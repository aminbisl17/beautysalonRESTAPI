package com.example.beautysalonRESTAPI.backend.model;

import java.sql.Time;
import java.sql.Timestamp;

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
@Table(name = "historiku", schema="beautysalon")
public class ClientHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name ="id_historikut")
    private Long id_historiku;

   //  @Column(name = "ID")
   // private Long id_personit; 

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

      @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID") 
    private Client client;

 //   public Long getId_personit() {
   //     return id_personit;
    //}

    //public void setId_personit(Long id_personit) {
      //  this.id_personit = id_personit;
    //}

    public Long getId_historiku() {
        return id_historiku;
    }

    public void setId_historiku(Long id_historiku) {
        this.id_historiku = id_historiku;
    }

    public int getId_sherbimit() {
        return id_sherbimit;
    }

    public void setId_sherbimit(int id_sherbimit) {
        this.id_sherbimit = id_sherbimit;
    }

    public int getId_atributit() {
        return id_atributit;
    }

    public void setId_atributit(int id_atributit) {
        this.id_atributit = id_atributit;
    }

    public Timestamp getData_sherbimit() {
        return data_sherbimit;
    }

    public void setData_sherbimit(Timestamp data_sherbimit) {
        this.data_sherbimit = data_sherbimit;
    }

    public Double getPagesa() {
        return pagesa;
    }

    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }

    public Double getQmimiBazik() {
        return qmimiBazik;
    }

    public void setQmimiBazik(Double qmimiBazik) {
        this.qmimiBazik = qmimiBazik;
    }

    public int getZbritja() {
        return zbritja;
    }

    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Time getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(Time kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }


}
