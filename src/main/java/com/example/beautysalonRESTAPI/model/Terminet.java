package com.example.beautysalonRESTAPI.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="terminet")
public class Terminet {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_terminit;

   @ManyToOne
   @JoinColumn(name="ID")
   private Client client;

   @ManyToOne
   @JoinColumn(name="employee_id")
   private Employees employee;

   @Column(name="pershkrimi")
   private String pershkrimi;

   @Column(name="data_caktimit")
   private LocalDateTime data_caktimit;

      @Column(name="data_krijimit", nullable = false)
   private LocalDateTime data_krijimit;


   @OneToMany(mappedBy = "terminet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Detajet_termineve> detajet_termineve;



      public LocalDateTime getData_krijimit() {
         return data_krijimit;
      }

      public void setData_krijimit(LocalDateTime data_krijimit) {
         this.data_krijimit = data_krijimit;
      }
      
      public List<Detajet_termineve> getDetajet_termineve() {
      return detajet_termineve;
   }

   public void setDetajet_termineve(List<Detajet_termineve> detajet_termineve) {
      this.detajet_termineve = detajet_termineve;
   }

      public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public Employees getEmployee() {
      return employee;
   }

   public void setEmployee(Employees employee) {
      this.employee = employee;
   }

   public Long getId_terminit() {
    return id_terminit;
   }

   public void setId_terminit(Long id_terminit) {
    this.id_terminit = id_terminit;
   }


   public String getPershkrimi() {
    return pershkrimi;
   }

   public void setPershkrimi(String pershkrimi) {
    this.pershkrimi = pershkrimi;
   }

   public LocalDateTime getData_caktimit() {
    return data_caktimit;
   }

   public void setData_caktimit(LocalDateTime data_caktimit) {
    this.data_caktimit = data_caktimit;
   }

}
