package com.example.beautysalonRESTAPI.backend.api.Company.Admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.beautysalonRESTAPI.backend.service.SherbimetService;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetClientDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Register.SherbimetRegisterDTO;
import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;

@RestController
@RequestMapping("api/admin")
public class AdminSherbimetController {

    @Autowired
    private SherbimetRepository sherbimetRepo;

     @PostMapping("/sherbimet/register")
    public ResponseEntity<String> registerService(@RequestBody SherbimetRegisterDTO request) {

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

        sherbimetRepo.save(sherbimi);

        return ResponseEntity.ok("Service registered successfully");
    }
}
