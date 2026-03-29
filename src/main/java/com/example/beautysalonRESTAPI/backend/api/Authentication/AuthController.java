package com.example.beautysalonRESTAPI.backend.api.Authentication;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.beautysalonRESTAPI.backend.model.AdminUser;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.Employees;
import com.example.beautysalonRESTAPI.backend.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.security.AuthRequest;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;
import com.example.beautysalonRESTAPI.backend.security.Responses.AdminAuthResponse;
import com.example.beautysalonRESTAPI.backend.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.backend.security.Responses.EmployeeAuthResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

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
        AdminUser adminUser = adminRepo.findByUsername(request.getUsername())
                                       .orElseThrow(() -> new RuntimeException("User not found"));

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

        return ResponseEntity.ok(new AdminAuthResponse(jwtUtil.generateToken(adminUser.getId(), adminUser.getUsername(), "ROLE_ADMIN")));

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
    try {authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        //UserDetails user = (UserDetails) authentication.getPrincipal();

        Employees employee = employeeRepo.findByUsername(request.getUsername())
                                         .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeAuthResponse response = new EmployeeAuthResponse(jwtUtil.generateToken(employee.getID(),employee.getUsername(), "ROLE_EMPLOYEE"));

        return ResponseEntity.ok(response);

  } catch (AuthenticationException e) {
        // This catches BadCredentialsException, UsernameNotFoundException, etc.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
    }
}

@PostMapping("/refresh-token")
public ResponseEntity<Map<String, String>> refreshToken(
        @CookieValue(value = "refreshToken", required = false) String refreshToken) {

    // No cookie found
    if (refreshToken == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Refresh token missing"));
    }

    // Validate refresh token
    if (!jwtUtil.validateRefreshToken(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(refreshToken);
    String role = jwtUtil.extractRole(refreshToken);
    Long ID = jwtUtil.extractId(refreshToken);
    String newAccessToken = jwtUtil.generateToken(ID, username, role);

    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
}
}