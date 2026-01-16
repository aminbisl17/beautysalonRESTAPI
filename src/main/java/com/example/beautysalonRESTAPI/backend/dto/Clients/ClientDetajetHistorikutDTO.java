package com.example.beautysalonRESTAPI.backend.dto.Clients;

import com.example.beautysalonRESTAPI.backend.model.Historiku_detajet;

public class ClientDetajetHistorikutDTO {
     private String emri_sherbimit;
     private String emri_atributit;
       private Double pagesa;
    private String pershkrimi;

        public ClientDetajetHistorikutDTO() {
    }
    
    // Constructor with Historiku_detajet parameter
    public ClientDetajetHistorikutDTO(Historiku_detajet historiku_detajet) {
        this.emri_sherbimit = historiku_detajet.getEmri_sherbimit();
        this.emri_atributit = historiku_detajet.getEmri_atributit();
        this.pagesa = historiku_detajet.getPagesa();
        this.pershkrimi = historiku_detajet.getPershkrimi();
    }
    
    
    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }
    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }
    public String getEmri_atributit() {
        return emri_atributit;
    }
    public void setEmri_atributit(String emri_atributit) {
        this.emri_atributit = emri_atributit;
    }
    public Double getPagesa() {
        return pagesa;
    }
    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
}
