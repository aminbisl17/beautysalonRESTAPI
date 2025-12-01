package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.time.LocalTime;

import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;

public class AtributetSherbimeveClientDTO {
    
    private String opsioni;
    private String pershkrimi;
    private Double qmimi;
    private Integer zbritja;
    private LocalTime kohezgjatja;

    
    public AtributetSherbimeveClientDTO(Atributet_sherbimeve a) {
        this.opsioni = a.getOpsioni();
        this.pershkrimi = a.getPershkrimi();
        this.qmimi = a.getQmimi();
        this.zbritja = a.getZbritja();
        this.kohezgjatja = a.getKohezgjatja();
    }

    public String getOpsioni() {
        return opsioni;
    }

    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Double getQmimi() {
        return qmimi;
    }

    public void setQmimi(Double qmimi) {
        this.qmimi = qmimi;
    }

    public Integer getZbritja() {
        return zbritja;
    }

    public void setZbritja(Integer zbritja) {
        this.zbritja = zbritja;
    }

    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
