package com.example.beautysalonRESTAPI.backend.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.beautysalonRESTAPI.backend.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.ClientRepository;
import com.example.beautysalonRESTAPI.backend.security.AuthRequest;
import com.example.beautysalonRESTAPI.backend.security.AuthResponse;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    
    @Autowired
    private AdminUserRepository adminUserRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    try {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String role = user.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.generateToken(user.getUsername(), role);

        Long userId = getUserIdFromDatabase(user.getUsername(), role);

        return ResponseEntity.ok(new AuthResponse(token, userId, role));

    } catch (AuthenticationException e) {
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}

    private Long getUserIdFromDatabase(String username, String role) {
    if (role.equals("ROLE_ADMIN")) {
        return adminUserRepo.findByUsername(username).get().getId();
    } else {
        return clientRepo.findByUsername(username).get().getId();
    }
}
}