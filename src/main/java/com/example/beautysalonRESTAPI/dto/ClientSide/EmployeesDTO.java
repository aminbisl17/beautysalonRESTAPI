package com.example.beautysalonRESTAPI.dto.ClientSide;

import com.example.beautysalonRESTAPI.model.Employees;

public class EmployeesDTO {
    
    private Long ID;
    private String emri;
    private String mbiemri;
    private String numri_telefonit;
    private String email;
    private String pershkrimi;

    public EmployeesDTO(){}

        public EmployeesDTO(Employees e) {
        this.ID = e.getID();
        this.emri = e.getEmri();
        this.mbiemri = e.getMbiemri();
        this.numri_telefonit = e.getNumri_telefonit();
        this.email = e.getEmail();
        this.pershkrimi = e.getPershkrimi();
    }


        public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }
    public String getEmri() {
        return emri;
    }
    public void setEmri(String emri) {
        this.emri = emri;
    }
    public String getMbiemri() {
        return mbiemri;
    }
    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }
    public String getNumri_telefonit() {
        return numri_telefonit;
    }
    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
}
