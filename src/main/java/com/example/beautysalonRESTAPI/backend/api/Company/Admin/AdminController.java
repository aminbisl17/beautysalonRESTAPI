package com.example.beautysalonRESTAPI.backend.api.Company.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.backend.dto.admin.UserDTO;
import com.example.beautysalonRESTAPI.backend.model.AdminUser;
import com.example.beautysalonRESTAPI.backend.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

@Autowired
private AdminUserRepository adminRepo;
          
  @Autowired
 private BCryptPasswordEncoder passwordEncoder;

  @Autowired
    private JwtUtil jwtUtil;

 @PostMapping("/register")
 public ResponseEntity<String> register(@RequestBody UserDTO request){


    if(adminRepo.findByUsername(request.getUsername()).isPresent()){
        return  ResponseEntity.badRequest().body("Username already exists");
    }

    var user = new AdminUser();
        user.setEmri(request.getEmri());
        user.setMbiemri(request.getMbiemri());
        user.setUsername(request.getUsername());
        user.setUserpassword(passwordEncoder.encode(request.getUserpassword()));
      //  user.setDateRegistered(request.getDateRegistered());

        adminRepo.save(user);

    return ResponseEntity.ok("User registered successfully");
 }

      @GetMapping("/data")
    public ResponseEntity<?> getAdminData(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // Extract username
        String username = jwtUtil.extractUsername(token);
 
        AdminUser user = adminRepo.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

 
        return ResponseEntity.ok(new UserDTO(user));
    }
    
}
