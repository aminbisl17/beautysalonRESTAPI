package com.example.beautysalonRESTAPI.backend.api.Company.Mixed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.AtributetImageResponse;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.backend.service.SherbimetService;

@RestController
@RequestMapping("api/mixed/sherbimet/")
public class MixedSherbimet {
    
    @Autowired
    private SherbimetService sherbimetService;

    @GetMapping("all")
    public List<SherbimetAdminDTO> getAllServices() {
        return sherbimetService.getAllServicesAdmin();
    }

    @GetMapping("atributet/{id}")
public ResponseEntity<AtributetImageResponse> getAtributet(@PathVariable Long id) throws IOException {

    List<Atributet_sherbimeve> atributet =
            sherbimetService.getAtributet(id);

    String imageName = sherbimetService.getServiceIMGPath(id);

    String base64Image = null;

    if (imageName != null && !imageName.isBlank()) {

        Path path = Paths.get("src/main/resources/SherbimetImgPath/", imageName);

        if (Files.exists(path)) {
            byte[] imageBytes = Files.readAllBytes(path);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        }
    }

    AtributetImageResponse response = new AtributetImageResponse();
    response.setImagePath(base64Image);
    response.setAtributet(atributet);

    return ResponseEntity.ok(response);
}
}
