package com.example.beautysalonRESTAPI.api;
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

import com.example.beautysalonRESTAPI.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.model.AdminUser;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.security.AuthRequest;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.service.QrSessionService;

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
          .domain("localhost")
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
public ResponseEntity<?> loginClient(@RequestBody AuthRequest request,  HttpServletResponse response) { 
    try { clientAuthManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Client client = clientRepo.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new RuntimeException("Client not found"));

       String jwtToken = jwtUtil.generateRefreshToken(client.getId(), client.getUsername(), "ROLE_CLIENT");

       ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken)
    .httpOnly(true)
    .secure(false) // true ONLY in HTTPS production
    .path("/")
    .maxAge(7 * 24 * 60 * 60)
    .sameSite("Lax") // OK for same-site localhost dev
    .build();

response.addHeader("Set-Cookie", cookie.toString());


        return ResponseEntity.ok(new ClientAuthResponse(client.getId(), jwtUtil.generateToken(client.getId(),client.getUsername(), "ROLE_CLIENT")));
  } catch (AuthenticationException e) {
    
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
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
               response = Map.of("token",cotoken);
    }
    else {
        return ResponseEntity.status(403)
                .body(Map.of("error", "Unauthorized role"));
    }

    messagingTemplate.convertAndSend("/topic/qr/" + request.getCode(),response);

    return ResponseEntity.ok(response);
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