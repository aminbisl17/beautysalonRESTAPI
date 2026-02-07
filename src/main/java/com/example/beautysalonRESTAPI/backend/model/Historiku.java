package com.example.beautysalonRESTAPI.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "historiku")
public class Historiku {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name ="id_historikut")
    @JsonIgnore
    private Long id_historiku;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_employee")
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID") 
    private Client client;

    @Column(name="data_sherbimit")
    private LocalDateTime data_sherbimit;

     @OneToMany(mappedBy = "historiku", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Historiku_detajet> detajet;


    public List<Historiku_detajet> getDetajet() {
        return detajet;
    }

     public void setDetajet(List<Historiku_detajet> detajet) {
         this.detajet = detajet;
     }

    public LocalDateTime getData_sherbimit() {
        return data_sherbimit;
    }

    public void setData_sherbimit(LocalDateTime data_sherbimit) {
        this.data_sherbimit = data_sherbimit;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId_historiku() {
        return id_historiku;
    }

    public void setId_historiku(Long id_historiku) {
        this.id_historiku = id_historiku;
    }


}
