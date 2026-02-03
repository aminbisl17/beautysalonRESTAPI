package com.example.beautysalonRESTAPI.backend.dto.terminet;

import java.time.LocalTime;

public class DetajetTermineveDTO {

    private Long sherbimetId;         // the service ID
    private Long atributetId;          // optional: the attribute ID (can be null)
    private LocalTime kohezgjatja;     
    private Double pagesa;
    

     public DetajetTermineveDTO(){}
    
    public Double getPagesa() {
        return pagesa;
    }
    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }
   
    public Long getSherbimetId() {
        return sherbimetId;
    }
    public void setSherbimetId(Long sherbimetId) {
        this.sherbimetId = sherbimetId;
    }
    public Long getAtributetId() {
        return atributetId;
    }
    public void setAtributetId(Long atributetId) {
        this.atributetId = atributetId;
    }
    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    // getters & setters
}
