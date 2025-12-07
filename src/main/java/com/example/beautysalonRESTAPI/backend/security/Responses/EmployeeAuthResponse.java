package com.example.beautysalonRESTAPI.backend.security.Responses;

import java.time.LocalDateTime;

import com.example.beautysalonRESTAPI.backend.model.Employees;

public class EmployeeAuthResponse {
    
    private long ID;
    private String emri;
    private String mbiemri;
    private String pershkrimi;
    private String username;
    private String gjinia;
    private String numri_telefonit;
    private String email;
    private LocalDateTime data_regjistrimit;
    private boolean is_active;
    private String token;
    private String role;

    public EmployeeAuthResponse(Employees emp, String token, String role) {
        this.ID = emp.getID();
        this.emri = emp.getEmri();
        this.mbiemri = emp.getMbiemri();
        this.pershkrimi = emp.getPershkrimi();
        this.username = emp.getUsername();
        this.gjinia = emp.getGjinia();
        this.numri_telefonit = emp.getNumri_telefonit();
        this.email = emp.getEmail();
        this.data_regjistrimit = emp.getData_regjistrimit();
        this.is_active = emp.isIs_active();
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }

    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getGjinia() { return gjinia; }
    public void setGjinia(String gjinia) { this.gjinia = gjinia; }

    public String getNumri_telefonit() { return numri_telefonit; }
    public void setNumri_telefonit(String numri_telefonit) { this.numri_telefonit = numri_telefonit; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getData_regjistrimit() { return data_regjistrimit; }
    public void setData_regjistrimit(LocalDateTime data_regjistrimit) { this.data_regjistrimit = data_regjistrimit; }

    public boolean isIs_active() { return is_active; }
    public void setIs_active(boolean is_active) { this.is_active = is_active; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
