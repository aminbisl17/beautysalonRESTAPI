package com.example.beautysalonRESTAPI.backend.api.Company.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.DashboardDTO;
import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;

@RestController
@RequestMapping("api/admin/dashboard")
public class AdminDashboardController {
    

    @Autowired
    ClientRepository clientRepo;

    @Autowired
    SherbimetRepository sherbimetRepo;

    @Autowired
    EmployeesRepository employeeRepo;

  

    @GetMapping("/statistics")
    ResponseEntity<DashboardDTO> getStatistics(){
        var dashboard = new DashboardDTO();
        dashboard.setClients(clientRepo.count());
        dashboard.setEmployees(employeeRepo.count());
        dashboard.setServices(sherbimetRepo.count());

    return ResponseEntity.ok(dashboard);
    }
}
