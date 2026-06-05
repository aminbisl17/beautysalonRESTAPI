package com.example.beautysalonRESTAPI.dto.Clients;

public class EmailVerificationDTO {
    public String otp;
    public String email;
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
