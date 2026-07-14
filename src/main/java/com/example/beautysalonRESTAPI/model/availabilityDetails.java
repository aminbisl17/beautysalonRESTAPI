package com.example.beautysalonRESTAPI.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="availabilityDetails")
public class availabilityDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_availability_details")
    private Long id_availability_details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_availability")
    @JsonIgnore
    private employeeAvailability empAvailability;

    @Column(name="day_of_week")
    private int day_of_week;

    @Column(name="start_time")
    private LocalTime start_time;

    @Column(name="end_time")
    private LocalTime end_time;

    @Column(name="pause_start")
    private LocalTime pause_start;

    @Column(name="pause_end")
    private LocalTime pause_end;

    public Long getId_availability_details() {
        return id_availability_details;
    }

    public void setId_availability_details(Long id_availability_details) {
        this.id_availability_details = id_availability_details;
    }

    public employeeAvailability getEmpAvailability() {
        return empAvailability;
    }

    public void setEmpAvailability(employeeAvailability empAvailability) {
        this.empAvailability = empAvailability;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(int day_of_week) {
        this.day_of_week = day_of_week;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public LocalTime getPause_start() {
        return pause_start;
    }

    public void setPause_start(LocalTime pause_start) {
        this.pause_start = pause_start;
    }

    public LocalTime getPause_end() {
        return pause_end;
    }

    public void setPause_end(LocalTime pause_end) {
        this.pause_end = pause_end;
    }
}
