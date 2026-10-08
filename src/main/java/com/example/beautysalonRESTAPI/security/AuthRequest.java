package com.example.beautysalonRESTAPI.security;

public class AuthRequest {
    private String username;
    private String password;
    private String tenantKey;
    
    public String getTenantKey() {
		return tenantKey;
	}
	public void setTenantKey(String tenantKey) {
		this.tenantKey = tenantKey;
	}
	public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}