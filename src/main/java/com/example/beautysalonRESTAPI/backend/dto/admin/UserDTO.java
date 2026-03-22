package com.example.beautysalonRESTAPI.backend.dto.admin;

import java.time.LocalDateTime;

import com.example.beautysalonRESTAPI.backend.model.AdminUser;

public class UserDTO {
    
    private Long ID;
    private String emri;
    private String mbiemri;
    private String username;
    private String userpassword;
    private LocalDateTime dateRegistered;

    public UserDTO(){}

    public UserDTO(AdminUser a){
        this.ID = a.getId();
        this.emri = a.getEmri();
        this.mbiemri = a.getMbiemri();
        this.username = a.getUsername();
        this.dateRegistered = a.getDateRegistered();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getEmri() {
        return emri;
    }
    public void setEmri(String emri) {
        this.emri = emri;
    }
    public String getMbiemri() {
        return mbiemri;
    }
    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserpassword() {
        return userpassword;
    }
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    } 
     public LocalDateTime getDateRegistered() {
        return dateRegistered;
    }
    public void setDateRegistered(LocalDateTime dateRegistered) {
        this.dateRegistered = dateRegistered;
    } 
}
