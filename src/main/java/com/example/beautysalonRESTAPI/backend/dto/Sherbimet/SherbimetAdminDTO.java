package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;

public class SherbimetAdminDTO {
    private Long ID;
    private String emri_sherbimit;
    private String pershkrimi;
    private Double qmimi_baze;
    private Boolean is_active;
    private LocalDateTime created_at;
    private LocalDateTime update_at;
    private int zbritja;
    private LocalTime kohezgjatja;

        public SherbimetAdminDTO(Sherbimet s) {
        this.ID = s.getID();
        this.emri_sherbimit = s.getEmri_sherbimit();
        this.pershkrimi = s.getPershkrimi();
        this.qmimi_baze = s.getQmimi_baze();
        this.is_active = s.is_active();
        this.created_at = s.getCreated_at();
        this.update_at = s.getUpdated_at();
        this.zbritja = s.getZbritja();
        this.kohezgjatja = s.getKohezgjatja();

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
    public Boolean getIs_active() {
        return is_active;
    }
    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    public LocalDateTime getUpdate_at() {
        return update_at;
    }
    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
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
}
