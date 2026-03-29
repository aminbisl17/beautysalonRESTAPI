package com.example.beautysalonRESTAPI.backend.api.Company.Admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.AtributetSherbimeveDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Register.SherbimetRegisterDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Update.SherbimetUpdateDTO;
import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;
import com.example.beautysalonRESTAPI.backend.service.SmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("api/admin/sherbimet")
public class AdminSherbimetController {

    @Autowired
    SmsService smsService;

  
   // AdminUser user;

    @Autowired
    private SherbimetRepository sherbimetRepo;

    @PostMapping("/register")
public ResponseEntity<Map<String, Object>> registerService(
        @RequestPart("data") String dataJson,  
        @RequestPart(value = "image", required = false) MultipartFile image,
        Authentication authentication
) throws IOException {

  
    ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JavaTimeModule());
mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
SherbimetRegisterDTO request = mapper.readValue(dataJson, SherbimetRegisterDTO.class);


    Sherbimet sherbimi = new Sherbimet();
    sherbimi.setEmri_sherbimit(request.getEmri_sherbimit());
    sherbimi.setPershkrimi(request.getPershkrimi());
    sherbimi.setQmimi_baze(request.getQmimi_baze());
    sherbimi.setZbritja(request.getZbritja());
    sherbimi.setKohezgjatja(request.getKohezgjatja());

    // Map attributes
    if (request.getAtributet() != null) {
        sherbimi.setAtributet(request.getAtributet().stream().map(attrDTO -> {
            Atributet_sherbimeve attr = new Atributet_sherbimeve();
            attr.setOpsioni(attrDTO.getOpsioni());
            attr.setPershkrimi(attrDTO.getPershkrimi());
            attr.setSherbimi(sherbimi);
            attr.setKohezgjatja(attrDTO.getKohezgjatja());
            attr.setQmimi(attrDTO.getQmimi());
            attr.setZbritja(attrDTO.getZbritja());
            return attr;
        }).collect(Collectors.toList()));
    }

    // Handle image
    if (image != null && !image.isEmpty()) {
        String uploadDir = "src/main/resources/SherbimetImgPath/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, image.getBytes());

        sherbimi.setImagepath(fileName);
    }

    //smsService.sendSms("+38345380871", "Sherbimi " + sherbimi.getEmri_sherbimit() + " eshte regjistruar me sukses!");
    sherbimetRepo.save(sherbimi);

    return ResponseEntity.ok(Map.of("message", "Service registered successfully"));
}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody SherbimetUpdateDTO request){

        Sherbimet sh = sherbimetRepo.findById(id).orElse(null);

        if(sh == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sherbimi nuk u gjet!");
        }

        sh.setEmri_sherbimit(request.getEmri_sherbimit());
        sh.setPershkrimi(request.getPershkrimi());
        sh.setQmimi_baze(request.getQmimi_baze());
        sh.setIs_active(request.getIs_active());
        sh.setZbritja(request.getZbritja());
        sh.setKohezgjatja(request.getKohezgjatja());
     
if (request.getAtributet() != null) {

    Map<Long, Atributet_sherbimeve> existingMap =
            sh.getAtributet().stream()
            .collect(Collectors.toMap(Atributet_sherbimeve::getId_atributit, a -> a));

    for (AtributetSherbimeveDTO dto : request.getAtributet()) {

        if (dto.getId_atributit() != null && existingMap.containsKey(dto.getId_atributit())) {
            // UPDATE existing attribute
            Atributet_sherbimeve attr = existingMap.get(dto.getId_atributit());
            attr.setOpsioni(dto.getOpsioni());
            attr.setPershkrimi(dto.getPershkrimi());
            attr.setKohezgjatja(dto.getKohezgjatja());
            attr.setQmimi(dto.getQmimi());
            attr.setZbritja(dto.getZbritja());

        } else {
            // ADD new attribute
            Atributet_sherbimeve attr = new Atributet_sherbimeve();
            attr.setOpsioni(dto.getOpsioni());
            attr.setPershkrimi(dto.getPershkrimi());
            attr.setKohezgjatja(dto.getKohezgjatja());
            attr.setQmimi(dto.getQmimi());
            attr.setZbritja(dto.getZbritja());
            attr.setSherbimi(sh);

            sh.getAtributet().add(attr);
        }
    }
}// 
      sherbimetRepo.save(sh);
      return ResponseEntity.ok("Service updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id){

         Sherbimet sh = sherbimetRepo.findById(id).orElse(null);

         if(sh == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sherbimi nuk u gjet!");
         }

         sherbimetRepo.delete(sh);
        return ResponseEntity.ok("Sherbimi u fshi!");
    }
}
