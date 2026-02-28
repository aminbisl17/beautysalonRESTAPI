package com.example.beautysalonRESTAPI.backend.dto.Sherbimet;

import java.util.List;

import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;

public class AtributetImageResponse{


    private String ImagePath;
    private List<Atributet_sherbimeve> atributet;

    
    public String getImagePath() {
        return ImagePath;
    }
    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
    public List<Atributet_sherbimeve> getAtributet() {
        return atributet;
    }
    public void setAtributet(List<Atributet_sherbimeve> atributet) {
        this.atributet = atributet;
    }
    
}