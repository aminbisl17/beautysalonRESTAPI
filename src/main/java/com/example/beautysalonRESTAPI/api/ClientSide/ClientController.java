package com.example.beautysalonRESTAPI.api.ClientSide;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.OtpClient;
import com.example.beautysalonRESTAPI.dto.Clients.ClientRegisterRequest;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.service.ApprovalService;
import com.example.beautysalonRESTAPI.service.SmsService;
import com.example.beautysalonRESTAPI.service.Clients.ClientService;
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

@Autowired ApprovalService approvalService;
        

  @Autowired
    private JwtUtil jwtUtil;


    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET client by ID
@GetMapping("/data")
public ResponseEntity<?> getClientById(@RequestHeader("Authorization") String authHeader) {

     if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("message","Missing or invalid Authorization header"));
        }

    String token = authHeader.substring(7);

    Client client = clientService.getClientById(jwtUtil.extractId(token));

    if (client == null) {
        return ResponseEntity.notFound().build();
    }

   // if (!client.getUsername().equals(auth.getName())) {
     //   return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    //}

    return ResponseEntity.ok(client);
}

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientRegisterRequest request) throws JsonProcessingException {
 
    
if (clientRepo.findByNumriTelefonit(request.getNumri_telefonit()).isPresent()) {
    return ResponseEntity.badRequest().body("User already exists");
}       System.out.println(request.getNumri_telefonit());
try{
        var client = new Client();
        client.setEmri(request.getEmri());
        client.setMbiemri(request.getMbiemri());
        client.setNumriTelefonit(request.getNumri_telefonit());
        client.setGjinia((Character.toLowerCase(request.getGjinia()) == 'm') ? "Mashkull"
                       : (Character.toLowerCase(request.getGjinia())) == 'f' ? "Femer" : "Asnjejes");
        client.setEmail(request.getEmail());
    

    String otp = smsservice.generateOTP();


  approvalService.createOtp(otp, client.getNumriTelefonit(), client);

  smsservice.sendOtp(client.getNumriTelefonit(),otp);
  
    
}
catch(Exception e){
         return ResponseEntity.badRequest().body("Unverified number!");   
}
   return ResponseEntity.ok("Client applied");
    
    }

    @PostMapping("/verify")
public ResponseEntity<String> verify(@RequestBody OtpClient response) throws JsonProcessingException {

    // Validate OTP first
    try {
        smsservice.validateOTP(response.getOtpcode(), response.getNumri_telefonit());
    } catch (IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Fetch approval safely
    Optional<Aprovals> approvalOpt = aprovalsRepo.findByUsername(response.getNumri_telefonit());
    if (approvalOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Approval not found for username: " + response.getNumri_telefonit());
    }

    Aprovals approval = approvalOpt.get();

    // Deserialize client data
    ObjectMapper objectMapper = new ObjectMapper();
    Client client = objectMapper.readValue(approval.getClient_data(), Client.class);
    clientRepo.save(client);

    aprovalsRepo.delete(approval);

    return ResponseEntity.ok("Client Verified and Registered!");
}

}