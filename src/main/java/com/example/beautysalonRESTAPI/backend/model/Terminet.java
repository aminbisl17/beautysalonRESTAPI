package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="terminet")
public class Terminet {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_terminit;

   @Column(name="ID")
   private Long ID;

   @Column(name="ID_sherbimit")
   private Long ID_sherbimit;

   @Column(name="id_atributit")
   private Long id_atributit;

   @Column(name="employee_id")
   private Long employee_id;

   @Column(name="pershkrimi")
   private String pershkrimi;

   @Column(name="data_caktimit")
   private LocalDateTime data_caktimit;

   @Column(name="kohezgjatja")
   private LocalTime kohezgjatja;

   @Column(name="qmimi")
   private Double qmimi;

   @Column(name="zbritja")
   private int zbritja;
   
   @Column(name="qmimi_fillestar")
   private Double qmimi_fillestar;

   public Long getId_terminit() {
    return id_terminit;
   }

   public void setId_terminit(Long id_terminit) {
    this.id_terminit = id_terminit;
   }

   public Long getID() {
    return ID;
   }

   public void setID(Long iD) {
    ID = iD;
   }

   public Long getID_sherbimit() {
    return ID_sherbimit;
   }

   public void setID_sherbimit(Long iD_sherbimit) {
    ID_sherbimit = iD_sherbimit;
   }

   public Long getId_atributit() {
    return id_atributit;
   }

   public void setId_atributit(Long id_atributit) {
    this.id_atributit = id_atributit;
   }

   public Long getEmployee_id() {
    return employee_id;
   }

   public void setEmployee_id(Long employee_id) {
    this.employee_id = employee_id;
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

   public LocalTime getKohezgjatja() {
    return kohezgjatja;
   }

   public void setKohezgjatja(LocalTime kohezgjatja) {
    this.kohezgjatja = kohezgjatja;
   }

   public Double getQmimi() {
    return qmimi;
   }

   public void setQmimi(Double qmimi) {
    this.qmimi = qmimi;
   }

   public int getZbritja() {
    return zbritja;
   }

   public void setZbritja(int zbritja) {
    this.zbritja = zbritja;
   }

   public Double getQmimi_fillestar() {
    return qmimi_fillestar;
   }

   public void setQmimi_fillestar(Double qmimi_fillestar) {
    this.qmimi_fillestar = qmimi_fillestar;
   }


}
