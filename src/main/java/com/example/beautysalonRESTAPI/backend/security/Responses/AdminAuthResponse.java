package com.example.beautysalonRESTAPI.backend.security.Responses;

import java.time.LocalDateTime;

import com.example.beautysalonRESTAPI.backend.model.AdminUser;

public class AdminAuthResponse{
private String token;
private Long userId;
private String emri;
private String mbiemri;
private String username;
private String role;
private LocalDateTime dateRegistered;

public AdminAuthResponse(AdminUser user, String token, String role) {
    this.userId = user.getId();
    this.emri = user.getEmri();
    this.mbiemri = user.getMbiemri();
    this.username = user.getUsername();
    this.dateRegistered = user.getDateRegistered();
    this.token = token;
    this.role = role;
}


public String getToken() { return token; }
public void setToken(String token) { this.token = token; }

public Long getUserId() { return userId; }
public void setUserId(Long userId) { this.userId = userId; }

public String getEmri() { return emri; }
public void setEmri(String emri) { this.emri = emri; }

public String getMbiemri() { return mbiemri; }
public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

public String getUsername() { return username; }
public void setUsername(String username) { this.username = username; }

public String getRole() { return role; }
public void setRole(String role) { this.role = role; }

public LocalDateTime getDateRegistered() { return dateRegistered; }
public void setDateRegistered(LocalDateTime dateRegistered) { this.dateRegistered = dateRegistered; 

}}