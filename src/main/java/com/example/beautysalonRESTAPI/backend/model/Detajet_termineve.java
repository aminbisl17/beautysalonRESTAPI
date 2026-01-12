package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="detajet_termineve")
public class Detajet_termineve {


        
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_detajetTermineve;

   @Column(name="kohezgjatja")
   private LocalTime kohezgjatja;

   @Column(name="pagesa")
   private Double pagesa;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="ID_sherbimit")
   private Sherbimet sherbimet;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="id_atributit")
   private Atributet_sherbimeve atributet_sherbimeve;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="id_terminit")
   private Terminet terminet;

   public Long getId_detajetTermineve() {
    return id_detajetTermineve;
   }

   public void setId_detajetTermineve(Long id_detajetTermineve) {
    this.id_detajetTermineve = id_detajetTermineve;
   }

   public LocalTime getKohezgjatja() {
    return kohezgjatja;
   }

   public void setKohezgjatja(LocalTime kohezgjatja) {
    this.kohezgjatja = kohezgjatja;
   }

     public Double getPagesa() {
    return pagesa;
}

   public void setPagesa(Double pagesa) {
    this.pagesa = pagesa;
   }


   public Sherbimet getSherbimet() {
    return sherbimet;
   }

   public void setSherbimet(Sherbimet sherbimet) {
    this.sherbimet = sherbimet;
   }

   public Atributet_sherbimeve getAtributet_sherbimeve() {
    return atributet_sherbimeve;
   }

   public void setAtributet_sherbimeve(Atributet_sherbimeve atributet_sherbimeve) {
    this.atributet_sherbimeve = atributet_sherbimeve;
   }

   public Terminet getTerminet() {
    return terminet;
   }

   public void setTerminet(Terminet terminet) {
    this.terminet = terminet;
   }

}
