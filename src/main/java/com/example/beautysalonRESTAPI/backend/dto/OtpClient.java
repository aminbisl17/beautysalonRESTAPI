package com.example.beautysalonRESTAPI.backend.dto;

public class OtpClient {
    
    private String otpcode, username;

    public OtpClient(){}

    public String getOtpcode() {
        return otpcode;
    }

    public void setOtpcode(String otpcode) {
        this.otpcode = otpcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
