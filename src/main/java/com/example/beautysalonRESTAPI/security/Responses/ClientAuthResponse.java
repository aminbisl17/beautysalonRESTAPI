package com.example.beautysalonRESTAPI.security.Responses;

import com.example.beautysalonRESTAPI.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.model.Client;

public class ClientAuthResponse {

 private String token;
//private ClientDTO client;

private Long ID;

public ClientAuthResponse(Long ID, String token) {
    this.ID = ID;
    this.token = token;
   // this.client = new ClientDTO(client);
    //this.client.setClientHistory(client.getClientHistory().stream().map(ClientHistoryDTO::new).toList());

}

// Getters and setters

public String getToken() { return token; }
public void setToken(String token) { this.token = token; }
public Long getID() {
    return ID;
}

public void setID(Long iD) {
    ID = iD;
}

/* 

public ClientDTO getClient() {
    return client;
}

 public void setClient(ClientDTO client) {
    this.client = client;
 }
 */
}