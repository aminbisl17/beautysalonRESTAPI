package com.example.beautysalonRESTAPI.backend.dto;

import java.time.LocalDateTime;

public class UserDTO {
    
    private String emri;
    private String mbiemri;
    private String username;
    private String userpassword;
    private LocalDateTime dateRegistered;

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
