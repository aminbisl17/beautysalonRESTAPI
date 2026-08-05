package com.example.beautysalonRESTAPI.api.ClientSide;

import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.ClientSide.EmployeesDTO;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Employee.employeeAvailabilityRepository;
import com.example.beautysalonRESTAPI.repository.Employee.skillsRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/web")
public class WebClient {
    
    @Autowired
    private EmployeesRepository employeesRepository;

       @Autowired
    private skillsRepository skillsRepo;
    
    @Autowired
    private employeeAvailabilityRepository availabilityRepository;

@GetMapping("employees/all")
public ResponseEntity<?> getEmployees() {

    List<EmployeesDTO> employees =
            employeesRepository.findEmployeesWithSkillsAndAvailability()
                    .stream()
                    .map(EmployeesDTO::new)
                    .toList();


    if(employees.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(employees);
}

}
