package com.example.beautysalonRESTAPI.backend.model;

import java.sql.Time;
import java.sql.Timestamp;

public class ClientHistoryDTO {
    private Long id_personit;
    private int id_historiku;
    private int id_sherbimit;
    private int id_atributit;
    private String emri_sherbimit;
    private String opsioni;
    private Timestamp data_sherbimit;
    private Double pagesa;
    private Double qmimiBazik;
    private int zbritja;
    private String pershkrimi;
    private Time kohezgjatja;
    // getters/setters
    public Long getId_personit() {
        return id_personit;
    }
    public void setId_personit(Long id_personit) {
        this.id_personit = id_personit;
    }
    public int getId_historiku() {
        return id_historiku;
    }
    public void setId_historiku(int id_historiku) {
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
    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }
    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }
    public String getOpsioni() {
        return opsioni;
    }
    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
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