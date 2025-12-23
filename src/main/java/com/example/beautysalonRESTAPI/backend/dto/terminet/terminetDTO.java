package com.example.beautysalonRESTAPI.backend.dto.terminet;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.beautysalonRESTAPI.backend.model.Terminet;

public class terminetDTO {
    
    private Long ID, ID_sherbimit, id_atributit, employee_id;
    private String pershkrimi;
    private LocalDateTime data_caktimit;
    private LocalTime kohezgjatja;
    private double qmimi, qmimi_fillestar;
    private int zbritja;

    public terminetDTO(Terminet t){

    }

    public terminetDTO(){

    }

    public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
    }
    public Long getID_sherbimit() {
        return ID_sherbimit;
    }
    public void setID_sherbimit(Long iD_sherbimit) {
        ID_sherbimit = iD_sherbimit;
    }
    public Long getId_atributit() {
        return id_atributit;
    }
    public void setId_atributit(Long id_atributit) {
        this.id_atributit = id_atributit;
    }
    public Long getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public LocalDateTime getData_caktimit() {
        return data_caktimit;
    }
    public void setData_caktimit(LocalDateTime data_caktimit) {
        this.data_caktimit = data_caktimit;
    }
    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
    public double getQmimi() {
        return qmimi;
    }
    public void setQmimi(double qmimi) {
        this.qmimi = qmimi;
    }
    public double getQmimi_fillestar() {
        return qmimi_fillestar;
    }
    public void setQmimi_fillestar(double qmimi_fillestar) {
        this.qmimi_fillestar = qmimi_fillestar;
    }
    public int getZbritja() {
        return zbritja;
    }
    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }
}
