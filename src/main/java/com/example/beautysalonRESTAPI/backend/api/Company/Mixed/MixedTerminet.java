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

@RestController
@RequestMapping("api/mixed/terminet/")
public class MixedTerminet {

    @Autowired
    SmsService smsService;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EmployeesRepository employeeRepo;

    @Autowired
    private TerminetRepository terminetRepository;

    @Autowired
    private SherbimetRepository sherbimetRepo;

    @Autowired
    private AtributetSherbimeveRepository atributetRepo;

    @PostMapping("create")
    public ResponseEntity<?> CreateAppointment(@RequestBody TerminetCreateDTO dto){

        Terminet termin = new Terminet();
termin.setClient(clientRepo.findById(dto.getClientId()).orElseThrow());
termin.setEmployee(employeeRepo.findById(dto.getEmployeeId()).orElseThrow());
termin.setPershkrimi(dto.getPershkrimi());
termin.setData_caktimit(dto.getDataCaktimit());

List<Detajet_termineve> detajetList = dto.getDetajetTermineve().stream().map(d -> {
    Detajet_termineve detaj = new Detajet_termineve();
    detaj.setTerminet(termin);
    detaj.setSherbimet(sherbimetRepo.findById(d.getSherbimetId()).orElseThrow());
    
    if(d.getAtributetId() != null) {
        detaj.setAtributet_sherbimeve(atributetRepo.findById(d.getAtributetId()).orElse(null));
    }

    detaj.setKohezgjatja(LocalTime.parse(d.getKohezgjatja()));
    return detaj;
}).collect(Collectors.toList());

termin.setDetajet_termineve(detajetList);
terminetRepository.save(termin);

     try {
        smsService.sendSms(dto.getNumri_tel(), "Termini juaj u krijua tek " + termin.getEmployee().getEmri());
    } catch (Exception e) {
        // Log the error but don’t block appointment creation
        e.printStackTrace(); // Or use a logger: log.error("Failed to send SMS", e);
    }
 
        return ResponseEntity.ok("Termini u krijua!");
    }

}
