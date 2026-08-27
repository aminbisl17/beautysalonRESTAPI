package com.example.beautysalonRESTAPI.dto.Sherbimet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import com.example.beautysalonRESTAPI.model.Sherbimet;

public class SherbimetAdminDTO {
    private Long ID;
    private String emri_sherbimit;
    private String pershkrimi;
    private Double qmimi_baze;
    private Boolean is_active;
    private LocalDateTime created_at;
    private LocalDateTime update_at;
    private int zbritja, kohezgjatja;
    private String imagePath;
    private Long avaSkillId;
    private List<AtributetSherbimeveDTO> atributet;
    
        public List<AtributetSherbimeveDTO> getAtributet() {
        return atributet;
    }

    public void setAtributet(List<AtributetSherbimeveDTO> atributet) {
        this.atributet = atributet;
    }

        public Long getAvaSkillId() {
        return avaSkillId;
    }

    public void setAvaSkillId(Long avaSkillId) {
        this.avaSkillId = avaSkillId;
    }

    public SherbimetAdminDTO(){}

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

         String imageName = s.getImagepath();

       //  String base64Image = null;

    if (imageName != null && !imageName.isBlank()) {

        Path path = Paths.get("src/main/resources/SherbimetImgPath/", imageName);

        if (Files.exists(path)) {
           // byte[] imageBytes;
            try {
               // imageBytes = Files.readAllBytes(path);
              //  base64Image = Base64.getEncoder().encodeToString((Files.readAllBytes(path)));
                this.imagePath = (Base64.getEncoder().encodeToString((Files.readAllBytes(path))));
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}

          public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
    public int getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
