package com.example.beautysalonRESTAPI.backend.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class AdminUser {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    
    @Column(name = "emri")
    private String emri;

    
    @Column(name = "mbiemri")
    private String mbiemri;

    
    @Column(name = "username")
    private String username;

    
    @Column(name = "userpassword")
    @JsonIgnore
    private String userpassword;

    
    @Column(name = "dateRegistered")
    private LocalDateTime dateRegistered;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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
