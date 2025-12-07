package com.example.beautysalonRESTAPI.backend.dto.employees;

import java.time.LocalDateTime;

import com.example.beautysalonRESTAPI.backend.model.Employees;

public class EmployeesDTO {
      private Long ID;
      private String emri, mbiemri, gjinia, pershkrimi, username, numri_telefonit, email;
      private LocalDateTime data_regjistrimit;
      private Boolean is_active;

        public EmployeesDTO(Employees employee) {
        this.ID = employee.getID();
        this.emri = employee.getEmri();
        this.mbiemri = employee.getMbiemri();
        this.gjinia = employee.getGjinia();
        this.pershkrimi = employee.getPershkrimi();
        this.username = employee.getUsername();
        this.numri_telefonit = employee.getNumri_telefonit();
        this.email = employee.getEmail();
        this.data_regjistrimit = employee.getData_regjistrimit();
        this.is_active = employee.isIs_active(); 
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
      public String getGjinia() {
          return gjinia;
      }
      public void setGjinia(String gjinia) {
          this.gjinia = gjinia;
      }
      public String getPershkrimi() {
          return pershkrimi;
      }
      public void setPershkrimi(String pershkrimi) {
          this.pershkrimi = pershkrimi;
      }
      public String getUsername() {
          return username;
      }
      public void setUsername(String username) {
          this.username = username;
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
      public LocalDateTime getData_regjistrimit() {
          return data_regjistrimit;
      }
      public void setData_regjistrimit(LocalDateTime data_regjistrimit) {
          this.data_regjistrimit = data_regjistrimit;
      }
      public Boolean getIs_active() {
          return is_active;
      }
      public void setIs_active(Boolean is_active) {
          this.is_active = is_active;
      }

}
