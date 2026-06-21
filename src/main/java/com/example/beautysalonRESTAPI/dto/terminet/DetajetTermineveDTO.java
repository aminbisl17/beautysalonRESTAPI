package com.example.beautysalonRESTAPI.dto.terminet;

public class DetajetTermineveDTO {

    private Long sherbimetId;         // the service ID
    private Long atributetId;          // optional: the attribute ID (can be null)
    private int kohezgjatja;     
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
    public int getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    // getters & setters
}
