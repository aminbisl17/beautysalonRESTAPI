package com.example.beautysalonRESTAPI.api.ClientSide;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.OtpClient;
import com.example.beautysalonRESTAPI.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientRegisterRequest;
import com.example.beautysalonRESTAPI.dto.Clients.EmailVerificationDTO;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.model.EmailVerificationOTP;
import com.example.beautysalonRESTAPI.repository.EmailVerificationRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.service.ApprovalService;
import com.example.beautysalonRESTAPI.service.EmailService;
import com.example.beautysalonRESTAPI.service.SmsService;
import com.example.beautysalonRESTAPI.service.Clients.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private SmsService smsservice;
   

    @Autowired
    private EmailService emailService;

@Autowired ApprovalService approvalService;
        

  @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailVerificationRepository emailRepo;


    @Autowired
    private ClientService clientService;


  
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
 
         
    
String Email = request.getEmail();

boolean emailExists =
    Email != null &&
    !Email.isEmpty() &&
    clientRepo.findByEmail(Email).isPresent();

boolean phoneExists =
        request.getNumri_telefonit() != null &&
        clientRepo.findByNumriTelefonit(request.getNumri_telefonit()).isPresent();

if (emailExists || phoneExists) {

    return ResponseEntity.badRequest().body("User already exists");
}   
try{
        var client = new Client();
        client.setEmri(request.getEmri());
        client.setMbiemri(request.getMbiemri());
        client.setNumriTelefonit(request.getNumri_telefonit());
        client.setEmailVerified(false);
        String gjinia = request.getGjinia();

client.setGjinia(
    "m".equalsIgnoreCase(gjinia) ? "Mashkull" :
    "f".equalsIgnoreCase(gjinia) ? "Femer" :
    "Asnjejes"
);
        String email = (request.getEmail().isEmpty() || request.getEmail() == null) ? null : request.getEmail();
        client.setEmail(email);
        

    String otp = smsservice.generateOTP();


  approvalService.createOtp(otp, client.getNumriTelefonit(), client);

  //smsservice.sendOtp(client.getNumriTelefonit(),otp);
  
    System.out.println(otp);
}
catch(Exception e){
    e.printStackTrace();
         return ResponseEntity.badRequest().body("Unverified number!");   
}
   return ResponseEntity.ok("Client applied");
    
    }

    @PostMapping("/verify")
public ResponseEntity<?> verify(@RequestBody OtpClient response) throws JsonProcessingException {

    // Validate OTP first
    try {
        approvalService.validateOTP(response.getOtpcode(), response.getNumri_telefonit());
    } catch (ResponseStatusException ex) {
           return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of("message", ex.getReason()));
    }
    catch (Exception ex) {
        return ResponseEntity
                .status(500)
                .body(
                    Map.of("message", ex.getMessage()));
    }

    return ResponseEntity.ok("Client Verified and Registered!");
}

@PostMapping("/send/email-verification-request")
public ResponseEntity<?> postMethodName(@RequestBody EmailVerificationDTO request) {
    try{
        emailRepo.deleteExpiredOtps();
    String otp = emailService.generateOTP();
    System.out.println(request.getEmail());
     emailService.sendOtp(request.getEmail(), "Kodi i verifikimit", otp);
     EmailVerificationOTP email = new EmailVerificationOTP();
     email.setEmail(request.getEmail());
     email.setOtp(otp);
     email.setCreatedAt(LocalDateTime.now());
     emailRepo.save(email);
    } catch(Exception e){
        e.printStackTrace();
    }
    return ResponseEntity.noContent().build();
}
@PatchMapping("/verify/email")
public ResponseEntity<?> verifyEmail(
        @RequestBody EmailVerificationDTO response,
        @RequestHeader("Authorization") String authHeader) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Missing or invalid Authorization header"));
    }

    String token = authHeader.substring(7);
    System.out.println(response.getEmail() + " " + response.getOtp());
    Client client = clientRepo.findById(jwtUtil.extractId(token)).orElse(null);

    if (client == null) {
        return ResponseEntity.notFound().build();
    }

    String result = emailService.verifyOtp(
            response.getEmail(),
            response.getOtp());

    if (!result.equals("Email verified successfully.")) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", result));
    }
    client.setEmail(response.getEmail());
    client.setEmailVerified(true);
    clientRepo.save(client);

    return ResponseEntity.ok(
            Map.of("message", "Email verified successfully."));
}

@PutMapping("/update")
public ResponseEntity<String> updateClient(
        @RequestBody ClientDTO response,
        @RequestHeader("Authorization") String authHeader) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7);

    Long id;
    try {
        id = jwtUtil.extractId(token);
    } catch (Exception e) {
        return ResponseEntity.status(401).body("Invalid token");
    }

    Client client = clientRepo.findById(id).orElse(null);

    if (client == null) {
        return ResponseEntity.status(404).body("Klienti nuk u gjet");
    }

    client.setEmri(response.getEmri());
    client.setMbiemri(response.getMbiemri());

    String gjinia = response.getGjinia();
    client.setGjinia(
            "m".equalsIgnoreCase(gjinia) ? "m" :
            "f".equalsIgnoreCase(gjinia) ? "f" :
            "a"
    );

    String email = response.getEmail();
    if (email != null && email.isBlank()) {
        email = null;
    }

    if (email != null) {
        Optional<Client> existing = clientRepo.findByEmail(email);
        if (existing.isPresent() && !existing.get().getId().equals(client.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }
        client.setEmail(email);
    } else{
          client.setEmail(null);
          client.setEmailVerified(false);
    }

    clientRepo.save(client);
    return ResponseEntity.ok("Te dhenat u perditesuan!");
}
}