package com.example.beautysalonRESTAPI.backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.dto.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.repository.sherbimetRepository;
@Service
public class sherbimetService {
    
    private final sherbimetRepository sherbimetRepo;

    public sherbimetService(sherbimetRepository sherbimetRepo){
           this.sherbimetRepo = sherbimetRepo;
    }

   public List<SherbimetAdminDTO> getAllServices() {
    return sherbimetRepo.findAll()
            .stream()
            .map(SherbimetAdminDTO::new)
            .toList();
}
}
