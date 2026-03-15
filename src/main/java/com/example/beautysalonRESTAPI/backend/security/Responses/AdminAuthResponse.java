package com.example.beautysalonRESTAPI.backend.security.Responses;


import com.example.beautysalonRESTAPI.backend.dto.admin.UserDTO;
import com.example.beautysalonRESTAPI.backend.model.AdminUser;

public class AdminAuthResponse{
private String token;
//private UserDTO user;


public AdminAuthResponse(String token) {

    this.token = token;
   // this.user = new UserDTO(user);
}


public String getToken() { return token; }
public void setToken(String token) { this.token = token; }

/* 
  public UserDTO getUser() {
    return user;  
}


public void setUser(UserDTO user) {
    this.user = user;
} */
}