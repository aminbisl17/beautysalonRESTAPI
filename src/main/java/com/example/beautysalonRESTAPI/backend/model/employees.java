package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employees")
public class employees {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name="emri")
    private String emri;

    @Column(name="mbiemri")
    private String mbiemri;

    @Column(name="pershkrimi")
    private String pershkrimi;

    @Column(name="username")
    private String username;

    @Column(name="userpassword")
    private String userpassword;

    @Column(name="gjinia")
    private String gjinia;

    @Column(name="numri_telefonit")
    private String numri_telefonit;

    @Column(name="email")
    private String email;

    @CreationTimestamp
    @Column(name="data_regjistrimit", nullable = false,  updatable = false)
    private LocalDateTime data_regjistrimit;

  @Column(name = "is_active", insertable = false, updatable = false)
    private boolean is_active;

    
    public String getGjinia() {
        return gjinia;
    }

    public void setGjinia(String gjinia) {
        this.gjinia = gjinia;
    }

    public String getNumri_telefonit() {
        return numri_telefonit;
    }

    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
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

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public LocalDateTime getData_regjistrimit() {
        return data_regjistrimit;
    }

    public void setData_regjistrimit(LocalDateTime data_regjistrimit) {
        this.data_regjistrimit = data_regjistrimit;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
