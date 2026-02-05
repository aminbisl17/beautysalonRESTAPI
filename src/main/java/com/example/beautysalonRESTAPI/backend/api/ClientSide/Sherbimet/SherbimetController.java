package com.example.beautysalonRESTAPI.backend.api.ClientSide.Sherbimet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.beautysalonRESTAPI.backend.service.SherbimetService;

@RestController
@RequestMapping("/web")
public class SherbimetController {
    //private final SherbimetService sherbimetService;

public SherbimetController(SherbimetService sherbimetService) {
 //   this.sherbimetService = sherbimetService;
}
/* 
 @GetMapping("/sherbimet/all")
    public List<SherbimetClientDTO> getAllServices() {
        return sherbimetService.getAllServicesClient();
    }
        */


}
