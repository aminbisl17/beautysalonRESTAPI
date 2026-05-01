package com.example.beautysalonRESTAPI.dto.Sherbimet.Register;

public class AtributetSherbimeveRegisterDTO {
    private String opsioni;
    private String pershkrimi_opsionit;
    private Double qmimi;
    private int zbritja, kohezgjatja;

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

    public int getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
