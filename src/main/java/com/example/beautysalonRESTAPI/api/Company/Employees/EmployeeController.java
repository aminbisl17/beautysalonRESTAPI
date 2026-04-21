package com.example.beautysalonRESTAPI.api.Company.Employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.security.JwtUtil;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    
        @Autowired
    private EmployeesRepository employeeRepo;

      @Autowired
    private JwtUtil jwtUtil;

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
 
        Employees user = employeeRepo.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

 
        return ResponseEntity.ok(new EmployeesDTO(user));
    }

}
