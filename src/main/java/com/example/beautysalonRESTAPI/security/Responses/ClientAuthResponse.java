package com.example.beautysalonRESTAPI.security.Responses;

import com.example.beautysalonRESTAPI.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.model.Client;

public class ClientAuthResponse {

 private String token;
//private ClientDTO client;

public ClientAuthResponse(String token) {
    this.token = token;
   // this.client = new ClientDTO(client);
    //this.client.setClientHistory(client.getClientHistory().stream().map(ClientHistoryDTO::new).toList());

}

// Getters and setters

public String getToken() { return token; }
public void setToken(String token) { this.token = token; }

}