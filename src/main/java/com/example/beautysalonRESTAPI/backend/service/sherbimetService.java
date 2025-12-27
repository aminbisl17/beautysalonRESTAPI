package com.example.beautysalonRESTAPI.backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetClientDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetEmployeeDTO;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;
@Service
public class SherbimetService {
    
    private final SherbimetRepository sherbimetRepo;

    public SherbimetService(SherbimetRepository sherbimetRepo){
           this.sherbimetRepo = sherbimetRepo;
    }

   public List<SherbimetAdminDTO> getAllServicesAdmin() {
    return sherbimetRepo.findAll()
            .stream()
            .map(SherbimetAdminDTO::new)
            .toList();
}

   public List<SherbimetEmployeeDTO> getAllServicesEmployee(){
    return sherbimetRepo.findAll()
             .stream().map(SherbimetEmployeeDTO::new)
             .toList();
   }
   public List<SherbimetClientDTO> getAllServicesClient(){
       return sherbimetRepo.findAll()
            .stream()
            .map(SherbimetClientDTO::new)
            .toList();
   }
}
