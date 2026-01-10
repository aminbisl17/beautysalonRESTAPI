package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.time.LocalTime;
import java.util.List;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;

public class SherbimetEmployeeDTO {
      private Long ID;
    private String emri_sherbimit;
    private String pershkrimi;
    private Double qmimi_baze;
    private int zbritja;
    private LocalTime kohezgjatja;
    private List<AtributetSherbimeveDTO> atributet;

    public SherbimetEmployeeDTO(Sherbimet s){
        this.ID = s.getID();
        this.emri_sherbimit = s.getEmri_sherbimit();
        this.pershkrimi = s.getPershkrimi();
        this.qmimi_baze = s.getQmimi_baze();
        this.zbritja = s.getZbritja();
        this.kohezgjatja = s.getKohezgjatja();

        if (s.getAtributet() != null) {
            this.atributet = s.getAtributet().stream()
                .map(AtributetSherbimeveDTO::new)
                .toList();
          
         }
    }
    public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
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
