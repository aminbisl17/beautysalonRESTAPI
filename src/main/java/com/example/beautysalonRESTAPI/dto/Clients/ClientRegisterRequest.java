package com.example.beautysalonRESTAPI.dto.Clients;

public class ClientRegisterRequest {
    private String emri;
    private String mbiemri;
    private String gjinia;
    private String numri_telefonit, email;

    public String getGjinia() 
    {
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
}