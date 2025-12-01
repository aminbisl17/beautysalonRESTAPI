package com.example.beautysalonRESTAPI.backend.api.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.beautysalonRESTAPI.backend.repository.Admin.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.security.AuthRequest;
import com.example.beautysalonRESTAPI.backend.security.AuthResponse;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;
import com.example.beautysalonRESTAPI.backend.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

      @Autowired
    private AdminUserRepository adminRepo;

    @Autowired
    private ClientRepository clientRepo;


    @Autowired
    private CustomUserDetailsService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

 @PostMapping("/login/admin")
public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
);
UserDetails user = (UserDetails) authentication.getPrincipal();
        Long userId = adminRepo.findByUsername(request.getUsername())
                               .orElseThrow()
                               .getId();

        String token = jwtUtil.generateToken(user.getUsername(), "ROLE_ADMIN");

        return ResponseEntity.ok(new AuthResponse(token, userId, "ROLE_ADMIN"));
    } catch (Exception e) {
        return ResponseEntity.status(401).body("Invalid admin username or password");
    }
}

@PostMapping("/login/client")
public ResponseEntity<?> loginClient(@RequestBody AuthRequest request) {
    try {
       Authentication authentication = authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
);
UserDetails user = (UserDetails) authentication.getPrincipal();
        Long userId = clientRepo.findByUsername(request.getUsername())
                                .orElseThrow()
                                .getId();

        String token = jwtUtil.generateToken(user.getUsername(), "ROLE_CLIENT");

        return ResponseEntity.ok(new AuthResponse(token, userId, "ROLE_CLIENT"));
    } catch (Exception e) {
        return ResponseEntity.status(401).body("Invalid client username or password");
    }
}
}