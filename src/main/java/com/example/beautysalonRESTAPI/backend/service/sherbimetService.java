package com.example.beautysalonRESTAPI.backend.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.model.Atributet_sherbimeve;
import com.example.beautysalonRESTAPI.backend.model.Sherbimet;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.AtributetSherbimeveRepository;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;

@Service
public class SherbimetService {
    
    private final SherbimetRepository sherbimetRepo;

    @Autowired
    private AtributetSherbimeveRepository atributetRepo;
    //private final AtributetSherbimeveRepository atributetRepo;

    public SherbimetService(SherbimetRepository sherbimetRepo){
           this.sherbimetRepo = sherbimetRepo;
        //   this.atributetRepo = atributetRepo;
    }

   public List<SherbimetAdminDTO> getAllServicesAdmin() {
    return sherbimetRepo.findAll()
            .stream()
            .map(SherbimetAdminDTO::new)
            .toList();
}

public List<Atributet_sherbimeve> getAtributet(Long id){
     return atributetRepo.getSpecificAtributes(id);
}


   public Sherbimet regiterService(Sherbimet s){
    return sherbimetRepo.save(s);
   }

  public String getServiceIMGPath(Long id) {

    Sherbimet sherbim = sherbimetRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Sherbimi not found"));

    return sherbim.getImagepath();
}
}
