package com.example.beautysalonRESTAPI.api.Company.Mixed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.employees.skillsDTO;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.model.Sherbimet;
import com.example.beautysalonRESTAPI.model.skills;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.repository.Employee.skillsRepository;
import com.example.beautysalonRESTAPI.repository.Sherbimet.SherbimetRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/com/mixed/employee/skills")
public class MixedSkills {
    
    @Autowired
    private skillsRepository skRepo;

    @Autowired
    private EmployeesRepository empRepo;

    @Autowired 
    private SherbimetRepository serRepo;

    
  @PostMapping("/add")
public ResponseEntity<?> addSkill(@RequestBody skillsDTO sk) {

    try {

        Employees emp = empRepo
                .findEmployeeById(sk.getId_employee())
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet"));

        List<skills> skillsList = new ArrayList<>();

        for(Long serviceId : sk.getId_services()) {

            Sherbimet ser = serRepo.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Sherbimi nuk u gjet"));

            skills skill = new skills();

            skill.setEmployees(emp);
            skill.setSherbimet(ser);

            skillsList.add(skill);
        }

        skRepo.saveAll(skillsList);

        return ResponseEntity.noContent().build();

    } catch(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
  
  @GetMapping("/all")
public ResponseEntity<?> getSkills() {

    try {
        return ResponseEntity.ok(skRepo.findAll());

    } catch(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    }
}
    


@GetMapping("/{id}")
public ResponseEntity<?> getSkill(@PathVariable Long id) {

    try {

        List<skills> skills = skRepo.findByEmployees_ID(id);

        if (skills.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        skillsDTO dto = new skillsDTO(skills);

        return ResponseEntity.ok(dto);

    } catch(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

 /* 
@PatchMapping("/{id}")
public ResponseEntity<?> updateSkill(
        @PathVariable Long id,
        @RequestBody skillsDTO sk) {

    try {

        skills skill = skRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill nuk u gjet"));


        if(sk.getId_employee() != null) {

            Employees emp = empRepo.findEmployeeById(sk.getId_employee())
                    .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet"));

            skill.setEmployees(emp);
        }


        if(sk.getId_service() != null) {

            Sherbimet ser = serRepo.findById(sk.getId_service())
                    .orElseThrow(() -> new RuntimeException("Sherbimi nuk u gjet"));

            skill.setSherbimet(ser);
        }


        skRepo.save(skill);

        return ResponseEntity.ok("Skill u perditesua");

    } catch(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

*/

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteSkill(@PathVariable Long id) {

    try {

        skills skill = skRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill nuk u gjet"));

        skRepo.delete(skill);

        return ResponseEntity.ok("Skill u fshi");

    } catch(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}
