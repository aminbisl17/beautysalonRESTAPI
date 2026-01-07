package com.example.beautysalonRESTAPI.backend.dto.Clients;

import java.sql.Time;
import java.sql.Timestamp;

import com.example.beautysalonRESTAPI.backend.model.ClientHistory;

public class ClientHistoryDTO {
   // private Long id_personit;
    private Long id_historiku;
    private String emri_sherbimit;
    private String emri_atributit;
    private Timestamp data_sherbimit;
    private Double pagesa;
    private Double qmimiBazik;
    private int zbritja;
    private String pershkrimi;
    private Time kohezgjatja;

    public ClientHistoryDTO(ClientHistory ch){
       this.id_historiku = ch.getId_historiku();
       this.emri_sherbimit = ch.getEmri_sherbimit();
       this.emri_atributit = ch.getEmri_atributit();
       this.data_sherbimit = ch.getData_sherbimit();
       this.pagesa = ch.getPagesa();
       this.qmimiBazik = ch.getQmimiBazik();
       this.zbritja = ch.getZbritja();
       this.pershkrimi = ch.getPershkrimi();
       this.kohezgjatja = ch.getKohezgjatja();
    }

        public ClientHistoryDTO(){
       
    }


        public String getEmri_atributit() {
        return emri_atributit;
    }

    public void setEmri_atributit(String emri_atributit) {
        this.emri_atributit = emri_atributit;
    }

    // getters/setters
 /*    public Long getId_personit() {
        return id_personit;
    }
    public void setId_personit(Long id_personit) {
        this.id_personit = id_personit;
    }*/
    public Long getId_historiku() {
        return id_historiku;
    }
    public void setId_historiku(Long id_historiku) {
        this.id_historiku = id_historiku;
    }

    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }
    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
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