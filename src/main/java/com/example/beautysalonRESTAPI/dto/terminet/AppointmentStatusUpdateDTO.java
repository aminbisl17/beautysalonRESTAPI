package com.example.beautysalonRESTAPI.dto.terminet;

import com.example.beautysalonRESTAPI.model.AppointmentStatus;

public class AppointmentStatusUpdateDTO {

    private AppointmentStatus status;
    private String cancellationReason;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}