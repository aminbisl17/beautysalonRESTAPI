package com.example.beautysalonRESTAPI.dto.Clients;

public class ClientRegisterRequest {
    private String emri;
    private String mbiemri;
    private char gjinia;
    private String numri_telefonit, email;
    private String username;
    private String password;

    public char getGjinia() 
    {
        return gjinia;
    }
    public void setGjinia(char gjinia) {
        this.gjinia = gjinia;
    }
    public String getNumri_telefonit() {
        return numri_telefonit;
    }
    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }

    // Getters and setters
     public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}