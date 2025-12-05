package com.example.beautysalonRESTAPI.backend.api.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.beautysalonRESTAPI.backend.service.sherbimetService;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetClientDTO;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.sherbimetRepository;

@RestController
@RequestMapping("api/admin")
public class AdminSherbimetController {
  
    private final sherbimetService sherbimetService;

public AdminSherbimetController(sherbimetService sherbimetService) {
    this.sherbimetService = sherbimetService;
}

     @GetMapping("/sherbimet/all")
    public List<SherbimetAdminDTO> getAllServices() {
        return sherbimetService.getAllServicesAdmin();
    }

   // @GetMappinng("/sherbimet/client")

}
