package com.example.beautysalonRESTAPI.security.Responses;

public class AdminAuthResponse{
private String token;

public AdminAuthResponse(String token) {

    this.token = token;
}


public String getToken() { return token; }
public void setToken(String token) { this.token = token; }

}