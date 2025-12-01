package com.example.beautysalonRESTAPI.backend.api.administration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.beautysalonRESTAPI.backend.service.sherbimetService;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.repository.sherbimetRepository;

@RestController
@RequestMapping("api/admin")
public class adminSherbimetController {
  
    private final sherbimetService sherbimetService;

public adminSherbimetController(sherbimetService sherbimetService) {
    this.sherbimetService = sherbimetService;
}

     @GetMapping("/sherbimet/all")
    public List<SherbimetAdminDTO> getAllServices() {
        return sherbimetService.getAllServices();
    }
}
