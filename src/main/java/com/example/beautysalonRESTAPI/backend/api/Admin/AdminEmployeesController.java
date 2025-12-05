package com.example.beautysalonRESTAPI.backend.api.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.employees.EmployeeRegisterDTO;
import com.example.beautysalonRESTAPI.backend.model.employees;
import com.example.beautysalonRESTAPI.backend.repository.employees.EmployeesRepository;

@RestController
@RequestMapping("api/admin")
public class AdminEmployeesController {
    
  @Autowired
  private EmployeesRepository employeesRepo;

    @Autowired
 private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/employees/register")
  public ResponseEntity<String> register(@RequestBody EmployeeRegisterDTO request){

       if(employeesRepo.findByUsername(request.getUsername()).isPresent()){
         return  ResponseEntity.badRequest().body("Username already exists");
       }

       var employees = new employees();

       employees.setEmri(request.getEmri());
       employees.setMbiemri(request.getMbiemri());
       employees.setPershkrimi(request.getPershkrimi());
       employees.setGjinia(request.getGjinia());
       employees.setEmail(request.getEmail());
       employees.setNumri_telefonit(request.getNumri_telefonit());
       employees.setUsername(request.getUsername());
       employees.setUserpassword(passwordEncoder.encode(request.getUserpassword()));

       employeesRepo.save(employees);
       
        return ResponseEntity.ok("Employee registered successfully");
  }

}
