package com.example.beautysalonRESTAPI.backend.api.Company.Mixed;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.terminet.TerminetCreateDTO;
import com.example.beautysalonRESTAPI.backend.model.Detajet_termineve;
import com.example.beautysalonRESTAPI.backend.model.Terminet;
import com.example.beautysalonRESTAPI.backend.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.AtributetSherbimeveRepository;
import com.example.beautysalonRESTAPI.backend.repository.Sherbimet.SherbimetRepository;
import com.example.beautysalonRESTAPI.backend.repository.employees.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.repository.terminet.TerminetRepository;
import com.example.beautysalonRESTAPI.backend.service.SmsService;
import com.example.beautysalonRESTAPI.backend.service.terminet.TerminetService;

@RestController
@RequestMapping("api/mixed/terminet/")
public class MixedTerminet {

    @Autowired
    SmsService smsService;

    @Autowired
    TerminetService terminetService;

    @Autowired
    private EmployeesRepository employeeRepo;



    @PostMapping("create")
    public ResponseEntity<?> CreateAppointment(@RequestBody TerminetCreateDTO dto){

     try {

          boolean success = terminetService.createAppointment(dto);
            if (success) {
                 smsService.sendSms(dto.getNumri_tel(), "Termini juaj u krijua tek " + (employeeRepo.findById(dto.getEmployeeId()).orElseThrow()).getEmri());
                return ResponseEntity.ok("Termini u krijua!");
            } else {
                return ResponseEntity.status(500).body("Failed to create appointment");
            }
    } catch (Exception e) {
        // Log the error but don’t block appointment creation
        e.printStackTrace(); // Or use a logger: log.error("Failed to send SMS", e);
    }
        return ResponseEntity.status(500).body("Termini nuk u krijua!");
    }

}
