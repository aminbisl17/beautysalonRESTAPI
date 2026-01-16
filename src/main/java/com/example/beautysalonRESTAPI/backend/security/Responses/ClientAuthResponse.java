package com.example.beautysalonRESTAPI.backend.security.Responses;

import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.backend.model.Client;

public class ClientAuthResponse {

 private String token;
 private ClientDTO client;

public ClientAuthResponse(Client client, String token) {
    this.token = token;
    this.client = new ClientDTO(client);
    this.client.setClientHistory(client.getClientHistory().stream().map(ClientHistoryDTO::new).toList());

}

// Getters and setters

public String getToken() { return token; }
public void setToken(String token) { this.token = token; }


public ClientDTO getClient() {
    return client;
}

 public void setClient(ClientDTO client) {
    this.client = client;
 }

}