package com.example.beautysalonRESTAPI.model;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="sherbimet")

public class Sherbimet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long ID;

    @Column(name="emri_sherbimit")
    private String emri_sherbimit;

    @Column(name="pershkrimi")
    private String pershkrimi;

    @Column(name="qmimi_baze")
    private Double qmimi_baze;

      @Column(name = "is_active", insertable = false, updatable = false)
    private boolean is_active;

     @CreationTimestamp
    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime created_at;

 @UpdateTimestamp
@Column(name = "updated_at")
private LocalDateTime updated_at;

    @Column(name="zbritja")
    private int zbritja;
    
    @Column(name="kohezgjatja")
    private LocalTime kohezgjatja;

   // @OneToMany(mappedBy = "sherbimi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//private List<Atributet_sherbimeve> atributet;

@OneToMany(mappedBy = "sherbimi",
           cascade = CascadeType.ALL,
           orphanRemoval = true,
           fetch = FetchType.EAGER)
private List<Atributet_sherbimeve> atributet;

  @Column(name="imagepath")
  private String imagepath;

    public String getImagepath() {
    return imagepath;
}

  public void setImagepath(String imagepath) {
    this.imagepath = imagepath;
  }

    public List<Atributet_sherbimeve> getAtributet() {
        return atributet;
    }

    public void setAtributet(List<Atributet_sherbimeve> atributet) {
        this.atributet = atributet;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }

    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Double getQmimi_baze() {
        return qmimi_baze;
    }

    public void setQmimi_baze(Double qmimi_baze) {
        this.qmimi_baze = qmimi_baze;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getZbritja() {
        return zbritja;
    }

    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }

    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
