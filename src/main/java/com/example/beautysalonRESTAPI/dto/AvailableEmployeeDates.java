package com.example.beautysalonRESTAPI.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.beautysalonRESTAPI.model.availabilityDetails;
import com.example.beautysalonRESTAPI.model.employeeAvailability;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AvailableEmployeeDates {

    private Long id_availability;

    @JsonIgnore
    private Long id_employee;

    private LocalDate start_date;
    private LocalDate end_date;
private List<AvailabilityDetails> availabilityDetails = new ArrayList<>();
    public AvailableEmployeeDates(){

    }

    public AvailableEmployeeDates(employeeAvailability e){
         this.id_availability = e.getIdAvailability();
         this.start_date = e.getStart_date();
         this.end_date = e.getEnd_date();
         this.availabilityDetails = e.getAvailabilityDetails() == null 
        ? List.of() 
        : e.getAvailabilityDetails()
            .stream()
            .map(AvailabilityDetails::new)
            .toList();
    }

    public Long getId_availability() {
      return id_availability;
   }

    public void setId_availability(Long id_availability) {
       this.id_availability = id_availability;
    }

    public Long getId_employee() {
       return id_employee;
    }

    public void setId_employee(Long id_employee) {
       this.id_employee = id_employee;
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

    public List<AvailabilityDetails> getAvailabilityDetails() {
        return availabilityDetails;
    }

    public void setAvailabilityDetails(List<AvailabilityDetails> availabilityDetails) {
        this.availabilityDetails = availabilityDetails;
    }


    public static class AvailabilityDetails {

        private Long id_availability_details;
        private int day_of_week;
        private LocalTime start_time;
        private LocalTime end_time;
        private LocalTime pause_start;
        private LocalTime pause_end;

        public AvailabilityDetails() {}
public AvailabilityDetails(availabilityDetails a) {
    this.id_availability_details = a.getId_availability_details();
    this.day_of_week = a.getDay_of_week();
    this.start_time = a.getStart_time();
    this.end_time = a.getEnd_time();
    this.pause_start = a.getPause_start();
    this.pause_end = a.getPause_end
    ();
}

     public Long getId_availability_details() {
            return id_availability_details;
        }
        public void setId_availability_details(Long id_availability_details) {
            this.id_availability_details = id_availability_details;
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
}