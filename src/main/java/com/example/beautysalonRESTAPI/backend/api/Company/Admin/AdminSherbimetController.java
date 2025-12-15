package com.example.beautysalonRESTAPI.backend.api.Company.Admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.beautysalonRESTAPI.backend.service.SherbimetService;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.AtributetSherbimeveDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetClientDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Register.SherbimetRegisterDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.Update.SherbimetUpdateDTO;
import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;

@RestController
@RequestMapping("api/admin/sherbimet")
public class AdminSherbimetController {

    @Autowired
    private SherbimetRepository sherbimetRepo;

     @PostMapping("/register")
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
      //  sh.setAtributet(request.getAtributet());
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
}
      sherbimetRepo.save(sh);
      return ResponseEntity.ok("Employee updated successfully");
    }
}
