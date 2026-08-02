package com.example.beautysalonRESTAPI.dto.terminet;

import java.util.ArrayList;
import java.util.List;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.model.Sherbimet;

public class DetajetStafitDTO {

    private List<Sherbimet> services;
    private List<AvailableEmployeeDates> dates = new ArrayList<>();

        public List<Sherbimet> getServices() {
        return services;
    }
    public void setServices(List<Sherbimet> services) {
        this.services = services;
    }
    public List<AvailableEmployeeDates> getDates() {
        return dates;
    }
    public void setDates(List<AvailableEmployeeDates> dates) {
        this.dates = dates;
    }


}
