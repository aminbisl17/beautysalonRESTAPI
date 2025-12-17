package com.example.beautysalonRESTAPI.backend.api.ClientSide.Client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientRegisterRequest;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.service.clients.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

        @Autowired
    private ClientRepository clientRepo;

        
  @Autowired
 private BCryptPasswordEncoder passwordEncoder;

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET client by ID
@GetMapping("/{id}")
public ResponseEntity<Client> getClientById(@PathVariable Long id, Authentication auth) {
    Client client = clientService.getClientById(id);
    if (client == null) {
        return ResponseEntity.notFound().build();
    }

    boolean isAdmin = auth.getAuthorities().stream()
                          .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Non-admins can only access their own data by username
    if (!isAdmin && !client.getUsername().equals(auth.getName())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(client);
}
  
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
    //    client.setDataRegjistrimit(request.getData_regjistrimit().toLocalDateTime());

    
        clientRepo.save(client);
    
        return ResponseEntity.ok("Client registered successfully");
    }
}