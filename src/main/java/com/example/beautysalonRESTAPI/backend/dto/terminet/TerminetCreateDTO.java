package com.example.beautysalonRESTAPI.backend.dto.terminet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.beautysalonRESTAPI.backend.model.Terminet;

public class TerminetCreateDTO {

    private Long clientId;            // the client making the appointment
    private Long employeeId;          // the employee assigned
    private String pershkrimi;        // description
    private LocalDateTime dataCaktimit; // appointment date/time

    private List<DetajetTermineveDTO> detajetTermineve;

    public TerminetCreateDTO(){}
    public TerminetCreateDTO(Terminet t){
               this.clientId = t.getClient().getId();
    this.employeeId = t.getEmployee().getID();
    this.pershkrimi = t.getPershkrimi();
    this.dataCaktimit = t.getData_caktimit();

    // Map each Detajet_termineve entity to DetajetTermineveDTO
    if (t.getDetajet_termineve() != null) {
        this.detajetTermineve = t.getDetajet_termineve().stream().map(d -> {
            DetajetTermineveDTO dto = new DetajetTermineveDTO();
            dto.setSherbimetId(d.getSherbimet().getID());
            dto.setAtributetId(d.getAtributet_sherbimeve() != null ? d.getAtributet_sherbimeve().getId_atributit() : null);
            dto.setKohezgjatja(d.getKohezgjatja().toString());
            return dto;
        }).collect(Collectors.toList());
    }
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public LocalDateTime getDataCaktimit() {
        return dataCaktimit;
    }

    public void setDataCaktimit(LocalDateTime dataCaktimit) {
        this.dataCaktimit = dataCaktimit;
    }

    public List<DetajetTermineveDTO> getDetajetTermineve() {
        return detajetTermineve;
    }

    public void setDetajetTermineve(List<DetajetTermineveDTO> detajetTermineve) {
        this.detajetTermineve = detajetTermineve;
    } 

}