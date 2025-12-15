package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.time.LocalTime;

import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;

public class AtributetSherbimeveDTO {
    
    private Long id_atributit;
    private String opsioni;
    private String pershkrimi_opsionit;
    private Double qmimi;
    private Integer zbritja;
    private LocalTime kohezgjatja;

    
    public AtributetSherbimeveDTO(Atributet_sherbimeve a) {
        this.id_atributit = a.getId_atributit();
        this.opsioni = a.getOpsioni();
        this.pershkrimi_opsionit = a.getPershkrimi();
        this.qmimi = a.getQmimi();
        this.zbritja = a.getZbritja();
        this.kohezgjatja = a.getKohezgjatja();
    }

     public AtributetSherbimeveDTO() {
     }

    

     
    public Long getId_atributit() {
        return id_atributit;
    }

    public void setId_atributit(Long id_atributit) {
        this.id_atributit = id_atributit;
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
