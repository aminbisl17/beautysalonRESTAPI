package com.example.beautysalonRESTAPI.backend.dto.terminet;

import java.time.LocalDateTime;
import java.util.List;

public class TerminetCreateDTO {

    private Long clientId;            
    private Long employeeId;          
    private String pershkrimi, numri_tel;
    private LocalDateTime dataCaktimit;

    private List<DetajetTermineveDTO> detajetTermineve;

    public TerminetCreateDTO(){}
    
   /*  public TerminetCreateDTO(Terminet t){
              this.clientId = clientId;
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

     */
     public String getNumri_tel() {
        return numri_tel;
    }
    public void setNumri_tel(String numri_tel) {
        this.numri_tel = numri_tel;
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