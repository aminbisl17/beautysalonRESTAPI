package com.example.beautysalonRESTAPI.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Aprovals")
public class Aprovals{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name="otp", nullable = false)
    private String otp;

    @Column(name="created", nullable = false)
    private LocalDateTime created;

@Column(name="client_data", columnDefinition = "NVARCHAR(MAX)")
private String client_data;

    public String getClient_data() {
        return client_data;
    }

    public void setClient_data(String client_data) {
        this.client_data = client_data;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}