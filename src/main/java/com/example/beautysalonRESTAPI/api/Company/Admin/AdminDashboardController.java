package com.example.beautysalonRESTAPI.api.Company.Admin;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.DashboardDTO;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.repository.Sherbimet.SherbimetRepository;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {
    

    @Autowired
    ClientRepository clientRepo;

    @Autowired
    SherbimetRepository sherbimetRepo;

    @Autowired
    EmployeesRepository employeeRepo;

  

    @GetMapping("/statistics")
    ResponseEntity<?> getStatistics(){
    return ResponseEntity.ok(Map.of("clients", clientRepo.count(), 
                                    "employees", employeeRepo.count(),
                                     "services", sherbimetRepo.count()));
    }
}
