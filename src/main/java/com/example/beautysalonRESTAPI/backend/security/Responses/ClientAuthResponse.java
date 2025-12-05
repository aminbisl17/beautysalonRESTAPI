package com.example.beautysalonRESTAPI.backend.security.Responses;

import java.time.LocalDateTime;
import java.util.List;

import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.ClientHistory;

public class ClientAuthResponse {

 private String token;
private Long userId;
private String emri;
private String mbiemri;
private String gjinia;
private String numriTelefonit;
private String username;
private LocalDateTime dataRegjistrimit;
private String pershkrimi;
private List<ClientHistory> clientHistory; // Optional: include if you want to send history
private String role;

public ClientAuthResponse(Client client, String token, String role) {
    this.userId = client.getId();
    this.emri = client.getEmri();
    this.mbiemri = client.getMbiemri();
    this.gjinia = client.getGjinia();
    this.numriTelefonit = client.getNumriTelefonit();
    this.username = client.getUsername();
    this.dataRegjistrimit = client.getDataRegjistrimit();
    this.pershkrimi = client.getPershkrimi();
    this.clientHistory = client.getClientHistory(); // optional
    this.token = token;
    this.role = role;
}

// Getters and setters

public String getToken() { return token; }
public void setToken(String token) { this.token = token; }

public Long getUserId() { return userId; }
public void setUserId(Long userId) { this.userId = userId; }

public String getEmri() { return emri; }
public void setEmri(String emri) { this.emri = emri; }

public String getMbiemri() { return mbiemri; }
public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

public String getGjinia() { return gjinia; }
public void setGjinia(String gjinia) { this.gjinia = gjinia; }

public String getNumriTelefonit() { return numriTelefonit; }
public void setNumriTelefonit(String numriTelefonit) { this.numriTelefonit = numriTelefonit; }

public String getUsername() { return username; }
public void setUsername(String username) { this.username = username; }

public LocalDateTime getDataRegjistrimit() { return dataRegjistrimit; }
public void setDataRegjistrimit(LocalDateTime dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }

public String getPershkrimi() { return pershkrimi; }
public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }

public List<ClientHistory> getClientHistory() { return clientHistory; }
public void setClientHistory(List<ClientHistory> clientHistory) { this.clientHistory = clientHistory; }

public String getRole() { return role; }
public void setRole(String role) { this.role = role; }

}