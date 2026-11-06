package com.example.beautysalonRESTAPI.dto.terminet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.Sherbimet.SherbimetAdminDTO;

public class DetajetStafitDTO {

    public record BusyRange(LocalDateTime start, LocalDateTime end) {}

    private List<SherbimetAdminDTO> services;
    private List<AvailableEmployeeDates> dates = new ArrayList<>();
    private List<BusyRange> unavailableDates = new ArrayList<>();

    public List<BusyRange> getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(List<BusyRange> unavailableDates) {
        this.unavailableDates = unavailableDates;
    }

    public List<SherbimetAdminDTO> getServices() {
        return services;
    }

    public void setServices(List<SherbimetAdminDTO> services) {
        this.services = services;
    }

    public List<AvailableEmployeeDates> getDates() {
        return dates;
    }

    public void setDates(List<AvailableEmployeeDates> dates) {
        this.dates = dates;
    }
}