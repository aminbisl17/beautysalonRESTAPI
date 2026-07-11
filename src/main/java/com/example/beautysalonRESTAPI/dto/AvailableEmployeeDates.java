package com.example.beautysalonRESTAPI.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AvailableEmployeeDates {

    private Long id_availability;

    @JsonIgnore
    private Long id_employee;

    private LocalDate start_date;
    private LocalDate end_date;

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


    private List<AvailabilityDetails> availabilityDetails;


    public List<AvailabilityDetails> getAvailabilityDetails() {
        return availabilityDetails;
    }

    public void setAvailabilityDetails(List<AvailabilityDetails> availabilityDetails) {
        this.availabilityDetails = availabilityDetails;
    }


    public static class AvailabilityDetails {

        private int day_of_week;
        private LocalTime start_time;
        private LocalTime end_time;
        private LocalTime pause_start;
        private LocalTime pause_end;


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