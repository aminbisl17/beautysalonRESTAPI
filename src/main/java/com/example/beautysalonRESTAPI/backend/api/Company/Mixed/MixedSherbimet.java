package com.example.beautysalonRESTAPI.backend.api.Company.Mixed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Atributet_sherbimeve> getAtributet(@PathVariable Long id){
        return sherbimetService.getAtributet(id);
    }

}
