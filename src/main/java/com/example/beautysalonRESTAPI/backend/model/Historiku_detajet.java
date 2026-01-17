package com.example.beautysalonRESTAPI.backend.model;

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
@Table(name="historiku_detajet")
public class Historiku_detajet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="id_historikut_detajet")
    private Long id_historikut_detajet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_historikut")
    private Historiku historiku;

    @Column(name="emri_sherbimit")
    private String emri_sherbimit;

    @Column(name="emri_atributit")
    private String emri_atributit;

    @Column(name="pershkrimi")
    private String pershkrimi;

    @Column(name="pagesa")
    private Double pagesa;

    public Long getId_historikut_detajet() {
        return id_historikut_detajet;
    }

    public void setId_historikut_detajet(Long id_historikut_detajet) {
        this.id_historikut_detajet = id_historikut_detajet;
    }

    public Historiku getHistoriku() {
        return historiku;
    }

    public void setHistoriku(Historiku historiku) {
        this.historiku = historiku;
    }

    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }

    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }

    public String getEmri_atributit() {
        return emri_atributit;
    }

    public void setEmri_atributit(String emri_atributit) {
        this.emri_atributit = emri_atributit;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Double getPagesa() {
        return pagesa;
    }

    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }
}
