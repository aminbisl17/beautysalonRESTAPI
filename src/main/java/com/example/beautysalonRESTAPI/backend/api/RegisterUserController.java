package com.example.beautysalonRESTAPI.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.ClientRegisterRequest;
import com.example.beautysalonRESTAPI.backend.repository.ClientRepository;


@RestController
@RequestMapping("/ClientRegister")
public class RegisterUserController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private ClientRepository clientRepo;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientRegisterRequest request) {
 
    //if (origin == null || !origin.equals("http://localhost:8080")) {
     //   return ResponseEntity.status(403).body("Registration allowed only from website");
    //}

        if (clientRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    
        var client = new com.example.beautysalonRESTAPI.backend.model.Client();
        client.setEmri(request.getEmri());
        client.setMbiemri(request.getMbiemri());
        client.setNumriTelefonit(request.getNumri_telefonit());
        client.setGjinia((Character.toLowerCase(request.getGjinia()) == 'm') ? "Mashkull"
                       : (Character.toLowerCase(request.getGjinia())) == 'f' ? "Femer" : "Asnjejes");
        client.setUsername(request.getUsername());
        client.setUserpassword(passwordEncoder.encode(request.getPassword()));

    
        clientRepo.save(client);
    
        return ResponseEntity.ok("Client registered successfully");
    }
}
