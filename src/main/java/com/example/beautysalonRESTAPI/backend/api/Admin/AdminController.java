package com.example.beautysalonRESTAPI.backend.api.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.admin.UserDTO;
import com.example.beautysalonRESTAPI.backend.model.AdminUser;
import com.example.beautysalonRESTAPI.backend.repository.Admin.AdminUserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

@Autowired
private AdminUserRepository adminRepo;
          
  @Autowired
 private BCryptPasswordEncoder passwordEncoder;


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
    
}
