package com.example.beautysalonRESTAPI.backend.security;

public class AuthResponse {

    private String token;
    private Long userId;
    private String role;

    public AuthResponse(String token, Long userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    public String getToken() { 
        return token; 
    }
    public void setToken(String token) { 
        this.token = token; 
    }

    public Long getUserId() { 
        return userId; 
    }
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }
}