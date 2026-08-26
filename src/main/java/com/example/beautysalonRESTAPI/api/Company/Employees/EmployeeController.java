package com.example.beautysalonRESTAPI.api.Company.Employees;

import java.util.List;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.employees.EmployeeCredentialsDTO;
import com.example.beautysalonRESTAPI.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.model.employeeAvailability;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Employee.employeeAvailabilityRepository;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.security.Responses.EmployeeAuthResponse;
import com.example.beautysalonRESTAPI.service.Employees.EmployeeAvailabilityDateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired 
    private employeeAvailabilityRepository employeeAvailabilityRepository;

    
    @Autowired
 private BCryptPasswordEncoder passwordEncoder;

      @GetMapping("/data")
    public ResponseEntity<?> getData(
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

    @PatchMapping("data")
    public ResponseEntity<?> updateData(@RequestHeader("Authorization") String authHeader, @RequestBody EmployeeCredentialsDTO request){

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        try {

  Employees employee = employeeRepo.findEmployeeById(jwtUtil.extractId(token)).orElse(null);

    if (employee == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Employee not found");
    }

    employee.setEmri(request.getEmri());
    employee.setMbiemri(request.getMbiemri());
    employee.setEmail(request.getEmail());
    employee.setNumri_telefonit(request.getNumri_telefonit());
    employee.setUsername(request.getUsername());

    if (request.getUserpassword() != null && !request.getUserpassword().isBlank()) {
        employee.setUserpassword(
                passwordEncoder.encode(request.getUserpassword())
        );
    
    }
    employeeRepo.save(employee);

    return ResponseEntity.ok("Data updated!");
    }
         catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
    }
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
    
    @GetMapping("/getAvailableDates")
    public ResponseEntity<?> getAvailableDates(@RequestHeader("Authorization") String authHeader){

          try{

               if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        
        
        String token = authHeader.substring(7);

  List<employeeAvailability> availability =
        employeeAvailabilityRepository.findByEmployees_ID(jwtUtil.extractId(token));

if (!availability.isEmpty()) {

    List<AvailableEmployeeDates> response = availability.stream()
            .map(AvailableEmployeeDates::new)
            .toList();

    return ResponseEntity.ok(response);

} else {
    return ResponseEntity.badRequest().body("not found!");
}
    }
    catch(Exception e){
return ResponseEntity.status(500).body(e.getMessage());
    }
    }
@PatchMapping("/updateAvailableDates/{availabilityId}")
public ResponseEntity<?> updateAvailableDates(
        @RequestHeader("Authorization") String authHeader, 
        @PathVariable Long availabilityId,
        @RequestBody AvailableEmployeeDates updatedData) {

    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        Long employeeIdFromToken = jwtUtil.extractId(token);

        // Call service layer to handle partial/full updates safely
        boolean success = employeeDates.updateAvailableEmployeeDates(availabilityId, employeeIdFromToken, updatedData);

        if (success) {
            return ResponseEntity.ok("Successfully updated availability!");
        } else {
            return ResponseEntity.status(404).body("Availability record not found or unauthorized");
        }

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.getMessage());
    }
}

@DeleteMapping("/deleteAvailableDates/{id}")
public ResponseEntity<?> deleteAvailableDates(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long id) {

    try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        Long employeeIdFromToken = jwtUtil.extractId(token);

        boolean deleted = employeeDates.deleteAvailableEmployeeDate(id, employeeIdFromToken);

        if (deleted) {
            return ResponseEntity.ok("Availability record deleted successfully.");
        } else {
            return ResponseEntity.status(404)
                    .body("No matching record found or you are not authorized to delete it.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
}
