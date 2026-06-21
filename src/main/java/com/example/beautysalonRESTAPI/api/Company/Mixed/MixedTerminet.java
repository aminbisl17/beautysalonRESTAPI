package com.example.beautysalonRESTAPI.api.Company.Mixed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.terminet.TerminetCreateDTO;
import com.example.beautysalonRESTAPI.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.service.SmsService;
import com.example.beautysalonRESTAPI.service.TerminetService;

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
               //  smsService.sendSms(dto.getNumri_tel(), "Termini juaj u krijua tek " + (employeeRepo.findById(dto.getEmployeeId()).orElseThrow()).getEmri());
                return ResponseEntity.ok("Termini u krijua!");
            } else {
                return ResponseEntity.status(500).body("Failed to create appointment");
            }
    } catch (Exception e) {
      
        e.printStackTrace();
    }
        return ResponseEntity.status(500).body("Termini nuk u krijua!");
    }

}
