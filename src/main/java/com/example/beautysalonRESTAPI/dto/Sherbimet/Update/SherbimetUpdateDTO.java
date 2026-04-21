package com.example.beautysalonRESTAPI.dto.Sherbimet.Update;

import java.time.LocalTime;
import java.util.List;

import com.example.beautysalonRESTAPI.dto.Sherbimet.AtributetSherbimeveDTO;

public class SherbimetUpdateDTO {
    
  private String emri_sherbimit;
    private String pershkrimi;
    private Double qmimi_baze;
    private int zbritja;
    private LocalTime kohezgjatja;
    private List<AtributetSherbimeveDTO> atributet;
    private boolean is_active;

    public boolean getIs_active() {
        return is_active;
    }
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
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
    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
    public List<AtributetSherbimeveDTO> getAtributet() {
        return atributet;
    }
    public void setAtributet(List<AtributetSherbimeveDTO> atributet) {
        this.atributet = atributet;
    }
}
