package com.example.beautysalonRESTAPI.api.Company.Employees;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.security.Responses.EmployeeAuthResponse;
import com.example.beautysalonRESTAPI.service.EmployeeAvailabilityDateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    
        @Autowired
    private EmployeesRepository employeeRepo;

      @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeAvailabilityDateService employeeDates;

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

    @PostMapping("/setAvailableDates")
    public ResponseEntity<?> setAvailableDates(@RequestHeader("Authorization") String authHeader, @RequestBody AvailableEmployeeDates a){
   
        try{

               if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

            a.setId_employee(jwtUtil.extractId(token));

            boolean success = employeeDates.setAvailableEmployeeDates(a);

            if(success){
                return ResponseEntity.ok("Success!!");
            } else{
              return ResponseEntity.status(500).body("Failed");
            }

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    

}
