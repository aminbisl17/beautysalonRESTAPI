package com.example.beautysalonRESTAPI.dto.Clients;

import java.time.LocalDateTime;
import java.util.List;

import com.example.beautysalonRESTAPI.model.Client;

public class ClientDTO {
    
    private Long ID;
    private String emri, mbiemri, numri_telefonit, pershkrimi, email;
    private LocalDateTime data_regjistrimit;
     private List<ClientHistoryDTO> clientHistory;

     public ClientDTO(){

     }
     public ClientDTO(Client c){
     this.ID = c.getId();
     this.emri = c.getEmri();
     this.mbiemri = c.getMbiemri();
     this.email = c.getEmail();
     this.pershkrimi = c.getPershkrimi();
     this.numri_telefonit = c.getNumriTelefonit();
     this.data_regjistrimit = c.getDataRegjistrimit();
     }
      public List<ClientHistoryDTO> getClientHistory() {
        return clientHistory;
    }
     public void setClientHistory(List<ClientHistoryDTO> clientHistory) {
         this.clientHistory = clientHistory;
     }
     public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
    }

       public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
}
