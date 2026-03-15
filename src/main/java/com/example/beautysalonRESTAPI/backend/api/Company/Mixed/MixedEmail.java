package com.example.beautysalonRESTAPI.backend.api.Company.Mixed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.mail.SimpleEmail;
import com.example.beautysalonRESTAPI.backend.service.EmailService;

@RestController
@RequestMapping("company/email/")
public class MixedEmail {

    @Autowired
    private EmailService emailService;
 
    @PostMapping("send")
    public ResponseEntity<String> sendEmail(@RequestBody SimpleEmail simpleEmail){
            
        emailService.send(simpleEmail.getTo(), simpleEmail.getSubject(), simpleEmail.getMessage());
        
        return ResponseEntity.ok("Email succesfully sent!");
    }
}
