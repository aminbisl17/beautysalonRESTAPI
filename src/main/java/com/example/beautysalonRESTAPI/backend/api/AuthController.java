package com.example.beautysalonRESTAPI.backend.api;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.beautysalonRESTAPI.backend.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.backend.model.AdminUser;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.Employees;
import com.example.beautysalonRESTAPI.backend.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.security.AuthRequest;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;
import com.example.beautysalonRESTAPI.backend.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.backend.service.QrSessionService;

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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


@PostMapping("/login/admin")
public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request, HttpServletResponse response) {
    try {   authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        
        Optional<AdminUser> optionalUser = adminRepo.findByUsername(request.getUsername());

         if (optionalUser.isEmpty()) {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
               .body("Invalid username or password");}

AdminUser adminUser = optionalUser.get();
/* 

          Cookie refreshCookie = new Cookie("refreshToken", jwtUtil.generateRefreshToken(adminUser.getUsername(), "ROLE_ADMIN"));
        refreshCookie.setHttpOnly(true);          // Prevent JS access
        refreshCookie.setSecure(true);            // Only HTTPS
        refreshCookie.setPath("/");               // Cookie valid for entire domain
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); 
        refreshCookie.setSecure(true);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");

        */

 
        String jwtToken = jwtUtil.generateRefreshToken(adminUser.getId(), adminUser.getUsername(), "ROLE_ADMIN");

        /* 
        Cookie refreshCookie = new Cookie("refreshToken", jwtToken);
refreshCookie.setHttpOnly(true);        // JS cannot access
refreshCookie.setSecure(false);         // Must be false for HTTP
refreshCookie.setPath("/");             // Valid for entire domain
refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

// Add SameSite=None via response header for cross-origin
response.addHeader("Set-Cookie",
    "refreshToken=" + jwtToken +
    "; Path=/; Max-Age=" + (7*24*60*60) +
    "; HttpOnly; SameSite=None; Secure=false"
);
        response.addCookie(refreshCookie);
 */
ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken)
        .httpOnly(true)
        .secure(false)          // false because localhost is HTTP
        .path("/")
          .domain("localhost")
        .maxAge(7 * 24 * 60 * 60)
        .sameSite("Lax")       // allows cross-origin POST
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
    try { authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Client client = clientRepo.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new RuntimeException("Client not found"));

       String jwtToken = jwtUtil.generateRefreshToken(client.getId(), client.getUsername(), "ROLE_CLIENT");

       ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken)
        .httpOnly(true)
        .secure(false)         
        .path("/")
          .domain("localhost")
        .maxAge(7 * 24 * 60 * 60)
        .sameSite("Lax")     
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
        authenticationManager.authenticate(
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
                .body("Invalid username or password");
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
public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {

    String refreshToken = body.get("refreshToken");

    if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(refreshToken);
    String role = jwtUtil.extractRole(refreshToken);
    Long id = jwtUtil.extractId(refreshToken);

    String newAccessToken = jwtUtil.generateToken(id, username, role);

    return ResponseEntity.ok(Map.of(
            "accessToken", newAccessToken
    ));
}
}