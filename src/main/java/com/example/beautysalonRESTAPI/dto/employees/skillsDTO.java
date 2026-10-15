package com.example.beautysalonRESTAPI.dto.employees;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        
        if (skill.getEmployees() != null) {
            this.id_employee = skill.getEmployees().getID();
        }


         //   String imageName = sherbimet.getImagepath();
//this.service.setImagepath();
            /* 
            if (imageName != null && !imageName.isBlank()) {
                Path path = Paths.get("src/main/resources/SherbimetImgPath/", imageName);

                if (Files.exists(path)) {
                    try {
                        byte[] imageBytes = Files.readAllBytes(path);
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        this.service.setImagepath(base64Image);
                    } catch (IOException e) {
                        e.printStackTrace();
                        this.service.setImagepath(null);
                    }
                } else {
                    // File does not exist on disk
                    this.service.setImagepath(null);
                }
            } else {
                // Image name in DB is null or blank
                this.service.setImagepath(null);
            } 

                
if (imageName != null && !imageName.isBlank()) {
    this.service.setImagepath("https://blobstorageamin.blob.core.windows.net/beautysalon-images/SherbimetImgPath/"
            + URLEncoder.encode(imageName, StandardCharsets.UTF_8)
              .replace("+", "%20"));
}
        }*/

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
     public void setId_employee(Long id_employee) {
		this.id_employee = id_employee;
	}

	public void setId_service(Long id_service) {
		this.id_service = id_service;
	}
}