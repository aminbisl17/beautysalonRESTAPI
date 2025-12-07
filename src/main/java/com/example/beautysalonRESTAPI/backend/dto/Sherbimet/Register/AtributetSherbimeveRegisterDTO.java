package com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Register;

import java.time.LocalTime;

import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;

public class AtributetSherbimeveRegisterDTO {
    private String opsioni;
    private String pershkrimi_opsionit;
    private Double qmimi;
    private Integer zbritja;
    private LocalTime kohezgjatja;

    public AtributetSherbimeveRegisterDTO() {
   
}

    public String getOpsioni() {
        return opsioni;
    }

    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
    }

    public String getPershkrimi() {
        return pershkrimi_opsionit;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi_opsionit = pershkrimi;
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
