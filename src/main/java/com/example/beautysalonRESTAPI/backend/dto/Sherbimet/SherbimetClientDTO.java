package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import com.example.beautysalonRESTAPI.backend.model.Sherbimet;

public class SherbimetClientDTO {
    private String emri_sherbimit, pershkrimi;
    private Double qmimi_baze;
    private int zbritja;

    public SherbimetClientDTO(Sherbimet s){
        this.emri_sherbimit = s.getEmri_sherbimit();
        this.pershkrimi = s.getPershkrimi();
        this.qmimi_baze = s.getQmimi_baze();
        this.zbritja = s.getZbritja();
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
    public int getZbritja() {
        return zbritja;
    }
    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }
}
