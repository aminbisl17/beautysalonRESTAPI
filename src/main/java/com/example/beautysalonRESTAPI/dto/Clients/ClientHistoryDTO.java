package com.example.beautysalonRESTAPI.dto.Clients;

import java.time.LocalDateTime;
import java.util.List;

import com.example.beautysalonRESTAPI.model.Historiku;

public class ClientHistoryDTO {
    private Long id_historikut;
    private String emri_mbiemri_klientit;
    private String emri_mbiemri_punonjesit;
       private LocalDateTime data_sherbimit;
   private List<ClientDetajetHistorikutDTO> detajet;
       
        public ClientHistoryDTO(){
       
    }
    
        public ClientHistoryDTO(Historiku h){
            this.id_historikut = h.getId_historiku();
         this.emri_mbiemri_klientit = h.getClient().getEmri() + " " + h.getClient().getMbiemri();
         this.emri_mbiemri_punonjesit = h.getEmri_mbiemri_punonjesit();
         this.data_sherbimit = h.getData_sherbimit();
    this.detajet = h.getDetajet() == null
        ? List.of()
        : h.getDetajet().stream().map(ClientDetajetHistorikutDTO::new).toList();
    }

     public Long getId_historikut() {
        return id_historikut;
    }

    public void setId_historikut(Long id_historikut) {
        this.id_historikut = id_historikut;
    }

       public List<ClientDetajetHistorikutDTO> getDetajet() {
    return detajet;
}

   public void setDetajet(List<ClientDetajetHistorikutDTO> detajet) {
    this.detajet = detajet;
   }


        public String getEmri_mbiemri_klientit() {
        return emri_mbiemri_klientit;
    }


       public void setEmri_mbiemri_klientit(String emri_mbiemri_klientit) {
           this.emri_mbiemri_klientit = emri_mbiemri_klientit;
       }


       public String getEmri_mbiemri_punonjesit() {
           return emri_mbiemri_punonjesit;
       }


       public void setEmri_mbiemri_punonjesit(String emri_mbiemri_punonjesit) {
           this.emri_mbiemri_punonjesit = emri_mbiemri_punonjesit;
       }


       public LocalDateTime getData_sherbimit() {
           return data_sherbimit;
       }


       public void setData_sherbimit(LocalDateTime data_sherbimit) {
           this.data_sherbimit = data_sherbimit;
       }

}