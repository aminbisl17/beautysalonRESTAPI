package com.example.beautysalonRESTAPI.api.Company.Mixed;

import com.example.beautysalonRESTAPI.repository.Sherbimet.SherbimetRepository;
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

import com.example.beautysalonRESTAPI.dto.Sherbimet.AtributetImageResponse;
import com.example.beautysalonRESTAPI.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.service.SherbimetService;

@RestController
@RequestMapping("api/mixed/sherbimet/")
public class MixedSherbimet {
    
    private final SherbimetRepository sherbimetRepository;
    @Autowired
    private SherbimetService sherbimetService;

    MixedSherbimet(SherbimetRepository sherbimetRepository) {
        this.sherbimetRepository = sherbimetRepository;
    }

    @GetMapping("all")
    public List<SherbimetAdminDTO> getAllServices() {
        return sherbimetService.getAllServices();
    }

    @GetMapping("atributet/{id}")
public ResponseEntity<?> getAtributet(@PathVariable Long id) throws IOException {

    if(!sherbimetRepository.findById(id).isPresent()){
        return ResponseEntity.notFound().build();
    }
    List<Atributet_sherbimeve> atributet =
            sherbimetService.getAtributet(id);

    /*
    String imageName = sherbimetService.getServiceIMGPath(id);

    String base64Image = null;

    if (imageName != null && !imageName.isBlank()) {

        Path path = Paths.get("src/main/resources/SherbimetImgPath/", imageName);

        if (Files.exists(path)) {
            byte[] imageBytes = Files.readAllBytes(path);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        }
    }
    */

    String imageName = sherbimetService.getServiceIMGPath(id);

String imageUrl = null;

if (imageName != null && !imageName.isBlank()) {
    imageUrl = "https://blobstorageamin.blob.core.windows.net/beautysalon-images/SherbimetImgPath/"
            + URLEncoder.encode(imageName, StandardCharsets.UTF_8)
              .replace("+", "%20");
}

    AtributetImageResponse response = new AtributetImageResponse();
    //response.setImagePath(base64Image);

    response.setImagePath(imageUrl);
    response.setAtributet(atributet);

    return ResponseEntity.ok(response);
}
}
