package com.example.beautysalonRESTAPI.backend.dto.terminet;

public class DetajetTermineveDTO {

    private Long sherbimetId;         // the service ID
    private Long atributetId;          // optional: the attribute ID (can be null)
    private String kohezgjatja;       // duration as "HH:mm" string
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
    public String getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(String kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    // getters & setters
}
