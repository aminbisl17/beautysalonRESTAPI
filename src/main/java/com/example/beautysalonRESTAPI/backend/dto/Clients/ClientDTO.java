package com.example.beautysalonRESTAPI.backend.dto.Clients;

import java.time.LocalDateTime;
import java.util.List;

import com.example.beautysalonRESTAPI.backend.model.Client;

public class ClientDTO {
    
    private String emri, mbiemri, username, gjinia, numri_telefonit;
    private LocalDateTime data_regjistrimit;
     private List<ClientHistoryDTO> ClientHistory;

     public ClientDTO(Client c){
     this.emri = c.getEmri();
     this.mbiemri = c.getMbiemri();
     this.gjinia = c.getGjinia();
     this.numri_telefonit = c.getNumriTelefonit();
     this.data_regjistrimit = c.getDataRegjistrimit();

     }
    public String getEmri() {
        return emri;
    }
    public void setEmri(String emri) {
        this.emri = emri;
    }
    public String getMbiemri() {
        return mbiemri;
    }
    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGjinia() {
        return gjinia;
    }
    public void setGjinia(String gjinia) {
        this.gjinia = gjinia;
    }
    public String getNumri_telefonit() {
        return numri_telefonit;
    }
    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }
    public LocalDateTime getData_regjistrimit() {
        return data_regjistrimit;
    }
    public void setData_regjistrimit(LocalDateTime data_regjistrimit) {
        this.data_regjistrimit = data_regjistrimit;
    }
    public List<ClientHistoryDTO> getClientHistory() {
        return ClientHistory;
    }
    public void setClientHistory(List<ClientHistoryDTO> clientHistory) {
        ClientHistory = clientHistory;
    }

}
