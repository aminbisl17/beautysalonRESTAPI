package com.example.beautysalonRESTAPI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients")
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

    @Column(name = "numri_telefonit", unique = true, nullable = false)
    private String numriTelefonit;

    @Column(name = "email", unique = false, nullable = true)
    private String email;

    @Column(name = "data_regjistrimit", insertable = false, updatable = false)
    private LocalDateTime dataRegjistrimit;

    @Column(name = "pershkrimi")
    private String pershkrimi;

    @Column(name = "username")
    private String username;
    
    @Column(name = "userpassword")
    private String userpassword;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Historiku> clientHistory;

    public List<Historiku> getClientHistory() {
        return clientHistory;
    }
    public void setClientHistory(List<Historiku> clientHistory) {
      this.clientHistory = clientHistory;
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

      public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public LocalDateTime getDataRegjistrimit() { return dataRegjistrimit; }
    public void setDataRegjistrimit(LocalDateTime dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }
    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }
    
} 

