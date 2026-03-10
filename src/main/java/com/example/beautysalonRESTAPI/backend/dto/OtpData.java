package com.example.beautysalonRESTAPI.backend.dto;

public class OtpData {

    private String otp;
    private long expiryTime;

    public OtpData(String otp, long expiryTime) {
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

    public String getOtp() {
        return otp;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}