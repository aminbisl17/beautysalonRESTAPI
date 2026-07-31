package com.example.beautysalonRESTAPI.dto.employees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.skills;

public class skillsDTO {

    private Long id;
    private Long id_employee;
    private Long id_service;
    private Sherbimet service;

    public skillsDTO(skills skill) {
        this.id = skill.getId();
        this.id_employee = skill.getEmployees().getID();
        this.id_service = skill.getSherbimet().getID();

              String imageName = skill.getSherbimet().getImagepath();

       //  String base64Image = null;

    if (imageName != null && !imageName.isBlank()) {

        Path path = Paths.get("src/main/resources/SherbimetImgPath/", imageName);

        if (Files.exists(path)) {
            try {
       
                 skill.getSherbimet().setImagepath((Base64.getEncoder().encodeToString((Files.readAllBytes(path)))));
            } catch (IOException e) {

                e.printStackTrace();
            }
        
        this.service = skill.getSherbimet();
    }
}
    }

    public Long getId() {
        return id;
    }

    public Long getId_employee() {
        return id_employee;
    }

    public Long getId_service() {
        return id_service;
    }

    public Sherbimet getService() {
        return service;
    }
}