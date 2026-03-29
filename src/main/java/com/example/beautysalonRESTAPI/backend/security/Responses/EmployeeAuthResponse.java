package com.example.beautysalonRESTAPI.backend.security.Responses;


public class EmployeeAuthResponse {

    private String token;


    public EmployeeAuthResponse( String token) {
 
        this.token = token;
    }



    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
