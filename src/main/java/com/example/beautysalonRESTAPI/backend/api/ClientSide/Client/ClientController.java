package com.example.beautysalonRESTAPI.backend.api.ClientSide.Client;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.OtpClient;
import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientRegisterRequest;
import com.example.beautysalonRESTAPI.backend.model.Aprovals;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.service.SmsService;
import com.example.beautysalonRESTAPI.backend.service.clients.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private SmsService smsservice;

    @Autowired
private AprovalsRepository aprovalsRepo;
        
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
    public ResponseEntity<String> register(@RequestBody ClientRegisterRequest request) throws JsonProcessingException {
 
    //if (origin == null || !origin.equals("http://localhost:8080")) {
     //   return ResponseEntity.status(403).body("Registration allowed only from website"); }
     
if (clientRepo.findByUsername(request.getUsername()).isPresent() ||
    clientRepo.findByEmail(request.getEmail()).isPresent() ||
    clientRepo.findByNumriTelefonit(request.getNumri_telefonit()).isPresent()) {
    return ResponseEntity.badRequest().body("User already exists");
}
        var client = new Client();
        client.setEmri(request.getEmri());
        client.setMbiemri(request.getMbiemri());
        client.setNumriTelefonit(request.getNumri_telefonit());
        client.setGjinia((Character.toLowerCase(request.getGjinia()) == 'm') ? "Mashkull"
                       : (Character.toLowerCase(request.getGjinia())) == 'f' ? "Femer" : "Asnjejes");
        client.setEmail(request.getEmail());
      //  client.setGjinia(request.getGjinia());
        client.setUsername(request.getUsername());
        client.setUserpassword(passwordEncoder.encode(request.getPassword()));
    //    client.setDataRegjistrimit(request.getData_regjistrimit().toLocalDateTime());

    
       ObjectMapper objectMapper = new ObjectMapper();
    String clientJson = objectMapper.writeValueAsString(client);

    // Generate OTP
    String otp = smsservice.generateOTP();

    Aprovals approval = new Aprovals();
    approval.setUsername(client.getUsername());
    approval.setOtp(Integer.parseInt(otp));
    approval.setCreated(LocalDateTime.now());
    approval.setClient_data(clientJson);

    aprovalsRepo.save(approval);

  smsservice.sendOtp(client.getNumriTelefonit(),otp);


     //   clientRepo.save(client);
    
        return ResponseEntity.ok("Client applied");
    }

  @PostMapping("/verify")
public ResponseEntity<String> verify(@RequestBody OtpClient response) throws JsonProcessingException {
    try {
        smsservice.validateOTP(response.getOtpcode(), response.getUsername());
    } catch (IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    Aprovals approval = aprovalsRepo.findByUsername(response.getUsername()).orElse(null);
    ObjectMapper objectMapper = new ObjectMapper();
    Client client = objectMapper.readValue(approval.getClient_data(), Client.class);
    clientRepo.save(client);

    return ResponseEntity.ok("Client Verified and Registered!");
}
}