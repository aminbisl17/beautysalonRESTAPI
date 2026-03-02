package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.util.List;

import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;

public class AtributetImageResponse{


    private String imagePath;
    private List<Atributet_sherbimeve> atributet;

    
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public List<Atributet_sherbimeve> getAtributet() {
        return atributet;
    }
    public void setAtributet(List<Atributet_sherbimeve> atributet) {
        this.atributet = atributet;
    }
    
}