package com.example.beautysalonRESTAPI.dto.employees;

import java.time.LocalDateTime;

public class EmployeeCredentialsDTO {
 
    private String emri, mbiemri, gjinia, pershkrimi, username, userpassword, numri_telefonit, email;
      private LocalDateTime data_regjistrimit;
  private Boolean isActive;

  public Boolean getIsActive() {
    return isActive;
}

public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
}
    public LocalDateTime getData_regjistrimit() {
        return data_regjistrimit;
    }

      public void setData_regjistrimit(LocalDateTime data_regjistrimit) {
          this.data_regjistrimit = data_regjistrimit;
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

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
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

}
