package com.example.beautysalonRESTAPI.model;
import jakarta.persistence.Table;

import java.time.LocalDate;
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

@Entity
@Table(name="employeeAvailability")
public class employeeAvailability {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_availability")
private Long idAvailability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_employee")
    @JsonIgnore
    private Employees employees;

    @Column(name="start_date")
    private LocalDate start_date;

    @Column(name="end_date")
    private LocalDate end_date;

     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sherbimet_id")
    @JsonIgnore
    private Sherbimet sherbimet;


      public Sherbimet getSherbimet() {
        return sherbimet;
    }

     public void setSherbimet(Sherbimet sherbimet) {
         this.sherbimet = sherbimet;
     }

      @OneToMany(mappedBy = "empAvailability", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
      private List<availabilityDetails> availabilityDetails;

    public List<availabilityDetails> getAvailabilityDetails() {
        return availabilityDetails;
    }

      public void setAvailabilityDetails(List<availabilityDetails> availabilityDetails) {
          this.availabilityDetails = availabilityDetails;
      }

public Long getIdAvailability() {
    return idAvailability;
}

public void setIdAvailability(Long idAvailability) {
    this.idAvailability = idAvailability;
}

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

}
