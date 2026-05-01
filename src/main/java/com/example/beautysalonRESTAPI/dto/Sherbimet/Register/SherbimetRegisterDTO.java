package com.example.beautysalonRESTAPI.dto.Sherbimet.Register;

import java.util.List;

public class SherbimetRegisterDTO {
     private String emri_sherbimit;
    private String pershkrimi;
    private Double qmimi_baze;
    private int zbritja, kohezgjatja;
    private List<AtributetSherbimeveRegisterDTO> atributet;

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
    public int getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
    public List<AtributetSherbimeveRegisterDTO> getAtributet() {
        return atributet;
    }
    public void setAtributet(List<AtributetSherbimeveRegisterDTO> atributet) {
        this.atributet = atributet;
    }
}
