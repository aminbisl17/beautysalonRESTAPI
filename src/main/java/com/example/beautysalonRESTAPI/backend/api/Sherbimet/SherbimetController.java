package com.example.beautysalonRESTAPI.backend.api.Sherbimet;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetClientDTO;
import com.example.beautysalonRESTAPI.backend.service.sherbimetService;

@RestController
@RequestMapping("/web")
public class SherbimetController {
    private final sherbimetService sherbimetService;

public SherbimetController(sherbimetService sherbimetService) {
    this.sherbimetService = sherbimetService;
}

 @GetMapping("/sherbimet/all")
    public List<SherbimetClientDTO> getAllServices() {
        return sherbimetService.getAllServicesClient();
    }
}
