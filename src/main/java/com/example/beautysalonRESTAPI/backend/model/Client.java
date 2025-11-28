package com.example.beautysalonRESTAPI.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients", schema = "beautysalon")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "emri")
    private String emri;

    @Column(name = "mbiemri")
    private String mbiemri;

    @Column(name = "gjinia")
    private String gjinia;

    @Column(name = "numri_telefonit")
    private String numriTelefonit;

    @Column(name = "data_regjistrimit")
    private LocalDate dataRegjistrimit;

    @Column(name = "pershkrimi")
    private String pershkrimi;

    @Column(name = "username")
    private String username;
    
    @Column(name = "userpassword")
    private String userpassword;

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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }
    public String getGjinia() { return gjinia; }
    public void setGjinia(String gjinia) { this.gjinia = gjinia; }
    public String getNumriTelefonit() { return numriTelefonit; }
    public void setNumriTelefonit(String numriTelefonit) { this.numriTelefonit = numriTelefonit; }
    public LocalDate getDataRegjistrimit() { return dataRegjistrimit; }
    public void setDataRegjistrimit(LocalDate dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }
    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }
    
} 

