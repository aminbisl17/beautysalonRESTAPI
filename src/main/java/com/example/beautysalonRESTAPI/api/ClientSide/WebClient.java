package com.example.beautysalonRESTAPI.api.ClientSide;

import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.ClientSide.EmployeesDTO;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;

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

@GetMapping("employees/all")
public ResponseEntity<?> getMethodName() {

    List<EmployeesDTO> employees =
        employeesRepository.findAllActiveEmployees()
                .stream()
                .map(emp -> new EmployeesDTO(emp))
                .toList();

    if(employees == null){
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok().body(employees);
}

@PostMapping("fastAuthenticate")
public String postMethodName(@RequestBody String entity) {
    //TODO: process POST request
    
    return entity;
}

}
