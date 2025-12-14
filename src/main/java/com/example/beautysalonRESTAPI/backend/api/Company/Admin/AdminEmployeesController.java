package com.example.beautysalonRESTAPI.backend.api.Company.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.employees.EmployeeCredentialsDTO;
import com.example.beautysalonRESTAPI.backend.dto.employees.EmployeesDTO;
import com.example.beautysalonRESTAPI.backend.model.Employees;
import com.example.beautysalonRESTAPI.backend.repository.employees.EmployeesRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminEmployeesController {
    
  @Autowired
  private EmployeesRepository employeesRepo;

    @Autowired
 private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/employees/register")
  public ResponseEntity<String> register(@RequestBody EmployeeCredentialsDTO request){

       if(employeesRepo.findByUsername(request.getUsername()).isPresent()){
         return  ResponseEntity.badRequest().body("Username already exists");
       }

       var employees = new Employees();

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

  @DeleteMapping("/employees/delete/{id}")
  public ResponseEntity<String> deleteEmploye(@PathVariable Long id, Authentication auth){
         Employees employee = employeesRepo.findEmployeeById(id).orElse(null);
         if(employee == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
  }

  employeesRepo.delete(employee);
  return ResponseEntity.ok("Employee deleted successfully");

}


@PutMapping("/employees/update/{id}")
public ResponseEntity<?> updateEmployee(
        @PathVariable Long id,
        @RequestBody EmployeeCredentialsDTO request) {

    Employees employee = employeesRepo.findEmployeeById(id).orElse(null);

    if (employee == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Employee not found");
    }

    employee.setEmri(request.getEmri());
    employee.setMbiemri(request.getMbiemri());
    employee.setPershkrimi(request.getPershkrimi());
    employee.setGjinia(request.getGjinia());
    employee.setEmail(request.getEmail());
    employee.setNumri_telefonit(request.getNumri_telefonit());
    employee.setUsername(request.getUsername());

    if (request.getUserpassword() != null && !request.getUserpassword().isBlank()) {
        employee.setUserpassword(
                passwordEncoder.encode(request.getUserpassword())
        );
    }

    employeesRepo.save(employee);

    return ResponseEntity.ok("Employee updated successfully");
}

  @GetMapping("/employees/all")
  public List<EmployeesDTO> getAllEmployees(){
     return employeesRepo.findAll().stream()
            .map(EmployeesDTO::new)
            .toList();
  }

}
