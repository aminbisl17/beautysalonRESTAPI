package com.example.beautysalonRESTAPI.dto.Clients;

public class ClientLogin {

    private String numri_telefonit, otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNumri_telefonit() {
        return numri_telefonit;
    }

    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }
}
