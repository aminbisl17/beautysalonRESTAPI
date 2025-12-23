package com.example.beautysalonRESTAPI.backend.api.Authentication;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.beautysalonRESTAPI.backend.model.AdminUser;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.Employees;
import com.example.beautysalonRESTAPI.backend.repository.Admin.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.repository.employees.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.security.AuthRequest;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;
import com.example.beautysalonRESTAPI.backend.security.Responses.AdminAuthResponse;
import com.example.beautysalonRESTAPI.backend.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.backend.security.Responses.EmployeeAuthResponse;
import com.example.beautysalonRESTAPI.backend.service.CustomUserDetailsService;

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
public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request) {
    try {   Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        AdminUser adminUser = adminRepo.findByUsername(request.getUsername())
                                       .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(adminUser.getUsername(), "ROLE_ADMIN");


        return ResponseEntity.ok(new AdminAuthResponse(adminUser, token, "ROLE_ADMIN"));

   } catch (AuthenticationException e) {
       
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
    }
}

@PostMapping("/login/client")
public ResponseEntity<?> loginClient(@RequestBody AuthRequest request) {
    try {  Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Client client = clientRepo.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new RuntimeException("Client not found"));

        String token = jwtUtil.generateToken(client.getUsername(), "ROLE_CLIENT");

        ClientAuthResponse response = new ClientAuthResponse(client, token, "ROLE_CLIENT");

        return ResponseEntity.ok(response);
  } catch (AuthenticationException e) {
    
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
    }
}

@PostMapping("/login/employee")
public ResponseEntity<?> loginEmployee(@RequestBody AuthRequest request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Employees employee = employeeRepo.findByUsername(user.getUsername())
                                         .orElseThrow(() -> new RuntimeException("Employee not found"));

        String token = jwtUtil.generateToken(employee.getUsername(), "ROLE_EMPLOYEE");

        EmployeeAuthResponse response = new EmployeeAuthResponse(employee, token, "ROLE_EMPLOYEE");

        return ResponseEntity.ok(response);

  } catch (AuthenticationException e) {
        // This catches BadCredentialsException, UsernameNotFoundException, etc.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Invalid username or password");
    }
}

@PostMapping("/auth/refresh")
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
    String newAccessToken = jwtUtil.generateToken(username, "ROLE_ADMIN");

    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
}
}