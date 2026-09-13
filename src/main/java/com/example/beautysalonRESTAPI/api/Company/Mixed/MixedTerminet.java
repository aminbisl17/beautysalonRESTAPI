package com.example.beautysalonRESTAPI.api.Company.Mixed;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.AvailableEmployeeDates;
import com.example.beautysalonRESTAPI.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.dto.terminet.DetajetStafitDTO;
import com.example.beautysalonRESTAPI.dto.terminet.TerminetCreateDTO;
import com.example.beautysalonRESTAPI.dto.terminet.TerminetGetDTO;
import com.example.beautysalonRESTAPI.model.employeeAvailability;
import com.example.beautysalonRESTAPI.model.skills;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Employee.availableSkillsRepository;
import com.example.beautysalonRESTAPI.repository.Employee.employeeAvailabilityRepository;
import com.example.beautysalonRESTAPI.repository.Employee.skillsRepository;
import com.example.beautysalonRESTAPI.service.SmsService;
import com.example.beautysalonRESTAPI.service.TerminetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/mixed/terminet/")
public class MixedTerminet {

    @Autowired
    private SmsService smsService;

    @Autowired
    private TerminetService terminetService;

    @Autowired
    private EmployeesRepository employeeRepo;

    @Autowired
    private skillsRepository skillsRepo;
    
    @Autowired
    private employeeAvailabilityRepository availabilityRepository;

    @Autowired
    private availableSkillsRepository avaSkillsRepo;

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
      
        e.printStackTrace();
    }
        return ResponseEntity.status(500).body("Termini nuk u krijua!");
    }


@GetMapping("employee-details/{id}")
public ResponseEntity<?> getMethodName(@PathVariable Long id) {

    if (!employeeRepo.existsById(id)) {
        return ResponseEntity.badRequest().body("punonjesi nuk u gjet!");
    }

    List<employeeAvailability> availability =
            availabilityRepository.findByEmployees_ID(id);

    DetajetStafitDTO data = new DetajetStafitDTO();
    data.setDates(
        availability.stream()
                .map(AvailableEmployeeDates::new)
                .toList()
    );

    return ResponseEntity.ok(data);
}

    @GetMapping("employee/{id}")
    public ResponseEntity<List<TerminetGetDTO>> getSpecificEmployeeAppointments(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                terminetService.getSpecificEmployeeAppointments(id)
        );
    }
}
