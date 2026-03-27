package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "attendance")
public class attendance {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long ID;

    @ManyToOne
    @JoinColumn(name="ID_Employee")
    public Employees employees;

    @Column(name="started_at")
    public LocalDateTime started_at;

     @Column(name="ended_at")
    public LocalDateTime ended_at;

     public Long getID() {
         return ID;
     }

     public void setID(Long iD) {
         ID = iD;
     }

     public Employees getEmployees() {
         return employees;
     }

     public void setEmployees(Employees employees) {
         this.employees = employees;
     }

     public LocalDateTime getStarted_at() {
         return started_at;
     }

     public void setStarted_at(LocalDateTime started_at) {
         this.started_at = started_at;
     }

     public LocalDateTime getEnded_at() {
         return ended_at;
     }

     public void setEnded_at(LocalDateTime ended_at) {
         this.ended_at = ended_at;
     }
}
