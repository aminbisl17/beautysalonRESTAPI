package com.example.beautysalonRESTAPI.api;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.OtpClient;
import com.example.beautysalonRESTAPI.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientLogin;
import com.example.beautysalonRESTAPI.model.AdminUser;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.repository.LoginOTPRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.security.AuthRequest;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.service.ApprovalService;
import com.example.beautysalonRESTAPI.service.LoginOTPService;
import com.example.beautysalonRESTAPI.service.QrSessionService;
import com.example.beautysalonRESTAPI.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
private SimpMessagingTemplate messagingTemplate
;
     @Autowired
    QrSessionService SessionService;

      @Autowired
    private AdminUserRepository adminRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EmployeesRepository employeeRepo;

        @Autowired
    private SmsService smsservice;

    @Autowired ApprovalService approvalService;

    @Autowired
    LoginOTPService loginOtpService;
        

@Autowired
private AprovalsRepository aprovalsRepo;
    //@Autowired
   // private AuthenticationManager authenticationManager;

   private final AuthenticationManager adminAuthManager;
private final AuthenticationManager clientAuthManager;
private final AuthenticationManager employeeAuthManager;

   public AuthController(
    @Qualifier("adminAuthManager") AuthenticationManager adminAuthManager,
    @Qualifier("clientAuthManager") AuthenticationManager clientAuthManager,
    @Qualifier("employeeAuthManager") AuthenticationManager employeeAuthManager
) {
    this.adminAuthManager = adminAuthManager;
    this.clientAuthManager = clientAuthManager;
    this.employeeAuthManager = employeeAuthManager;
}
    @Autowired
    private JwtUtil jwtUtil;


@PostMapping("/login/admin")
public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request, HttpServletResponse response) {
    try {   adminAuthManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        
        Optional<AdminUser> optionalUser = adminRepo.findByUsername(request.getUsername());

         if (optionalUser.isEmpty()) {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
               .body("Invalid username or password");}

AdminUser adminUser = optionalUser.get();

        String jwtToken = jwtUtil.generateRefreshToken(adminUser.getId(), adminUser.getUsername(), "ROLE_ADMIN");

 
ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken)
        .httpOnly(true)
        .secure(false)         
        .path("/")
     //     .domain("localhost")
        .maxAge(7 * 24 * 60 * 60)
        .sameSite("Lax")     
        .build();

response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(Map.of("refreshToken",jwtToken, "token", jwtUtil.generateToken(adminUser.getId(), adminUser.getUsername(), "ROLE_ADMIN")));

   } catch (AuthenticationException e) {
       
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
    }
}


@PostMapping("/login/client")
public ResponseEntity<?> loginClient(@RequestBody ClientLogin response) { 

   Client client = clientRepo.findByNumriTelefonit(response.getNumri_telefonit()).orElse(null);

    if(client == null){
        return ResponseEntity.badRequest().body("ky numer nuk ekziston!");
    }

    String otp = smsservice.generateOTP();

    // approvalService.createOtp(otp, client.getNumriTelefonit(), null);
    loginOtpService.createLoginOTP(otp, response.getNumri_telefonit());
      //smsservice.sendOtp(response.getNumri_telefonit(), otp);
      System.out.println("OTP: " + otp);
    return ResponseEntity.ok("sent to verify");
}

@PostMapping("/login/client/verify")
public ResponseEntity<?> verify(@RequestBody OtpClient response, HttpServletResponse res) {

    try {
     loginOtpService.validateLoginOTP(
                response.getOtpcode(),
                response.getNumri_telefonit()
        );

        Client client = clientRepo.findByNumriTelefonit(response.getNumri_telefonit())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        String accessToken = jwtUtil.generateToken(
                client.getId(),
                client.getNumriTelefonit(),
                "ROLE_CLIENT"
        );

        String refreshToken = jwtUtil.generateRefreshToken(
                client.getId(),
                client.getNumriTelefonit(),
                "ROLE_CLIENT"
        );

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(30 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        res.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(new ClientAuthResponse(accessToken));

    } catch (ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of("message", ex.getReason()));

    } catch (Exception ex) {
        return ResponseEntity
                .status(500)
                .body(
                    Map.of("message", ex.getMessage()));
    }
}
@PostMapping("/login/employee")
public ResponseEntity<?> loginEmployee(@RequestBody AuthRequest request) {

    try {
         employeeAuthManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<Employees> optionalEmployee =
                employeeRepo.findByUsername(request.getUsername());

        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        Employees employee = optionalEmployee.get();
        return ResponseEntity.ok(Map.of(
            
            "refreshToken", jwtUtil.generateRefreshToken(employee.getID(), employee.getUsername(), "ROLE_EMPLOYEE")
              ,"token", jwtUtil.generateToken(
                        employee.getID(),
                        employee.getUsername(),
                        "ROLE_EMPLOYEE"
                )));

    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }
}

@GetMapping("/generate-qr_code")
  public ResponseEntity<Map<String, String>> sendQrCode(){
    return ResponseEntity.ok(Map.of("code", SessionService.generateQrCode()));
  }


@PostMapping("/validate-qr_code")
public ResponseEntity<?> validateQrCode(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody QrSessionDTO request) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7);

    if (!jwtUtil.validateToken(token)) {
        return ResponseEntity.status(401).body("Invalid or expired token");
    }

    if (!SessionService.validateQr(request.getCode())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid or expired QR code"));
    }

    String role = jwtUtil.extractRole(token);
    Long id = jwtUtil.extractId(token);

    Object response;

    if ("ROLE_EMPLOYEE".equals(role) ||  "ROLE_ADMIN".equals(role)) {
        String cotoken = jwtUtil.generateCompanyToken(id, token, role);
               response = Map.of("token",cotoken, "role", role, "username", jwtUtil.extractUsername(token));
    }
    else {
        return ResponseEntity.status(403)
                .body(Map.of("error", "Unauthorized role"));
    }

    messagingTemplate.convertAndSend("/topic/qr/" + request.getCode(),response);

    return ResponseEntity.ok(response);
}


@PostMapping("/delete-refresh-token")
public ResponseEntity<?> deleteRefreshToken(HttpServletResponse response){
    ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .secure(false)   
            .path("/")       
            .maxAge(0)       
            .sameSite("Lax")
            .build();

    response.setHeader("Set-Cookie", cookie.toString());

       return ResponseEntity.noContent().build();
}

@PostMapping("/refresh-token")
public ResponseEntity<?> refresh(
        @CookieValue(value = "refreshToken", required = false) String cookieToken,
        @RequestBody(required = false) Map<String, String> body
) {
   String refreshToken = cookieToken != null
            ? cookieToken
            : (body != null ? body.get("refreshToken") : null);


    if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(refreshToken);
    String role = jwtUtil.extractRole(refreshToken);
    Long id = jwtUtil.extractId(refreshToken);

    String newAccessToken = jwtUtil.generateToken(id, username, role);

    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
}
}