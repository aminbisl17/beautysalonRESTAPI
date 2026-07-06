package com.example.beautysalonRESTAPI.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tools.jackson.databind.ObjectMapper;

@Service
public class ApprovalService {
    
    
        @Autowired
    private ClientRepository clientRepo;

    private final AprovalsRepository aproval; 

    ApprovalService(AprovalsRepository aproval){
        this.aproval = aproval;
    }

    @Transactional
    public void createOtp(String otp, String username, Client client) {

        aproval.deleteExpiredOtps();
        aproval.deleteByUsername(username);

        Aprovals approval = new Aprovals();
        approval.setUsername(username);
        approval.setOtp(otp);
        approval.setCreated(LocalDateTime.now());

        if(client != null){
            
          ObjectMapper objectMapper = new ObjectMapper();
        String clientJson = objectMapper.writeValueAsString(client);
        approval.setClient_data(clientJson);
        }
        aproval.saveAndFlush(approval);
    }

@Transactional(noRollbackFor = ResponseStatusException.class)
    public boolean validateOTP(String userInputOtp, String username) {

      //   aproval.deleteExpiredOtps();
         
        Aprovals approval = aproval.findByUsernameAndOtp(username, userInputOtp)
                .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "OTP does not exist or is incorrect"
                    ));

                   LocalDateTime cutoff = LocalDateTime.now(ZoneOffset.UTC).minusMinutes(5);
                   
       if(approval.getCreated().isBefore(cutoff)){

            aproval.deleteByUsernameAndOtp(username, userInputOtp);

           throw new ResponseStatusException(
                HttpStatus.GONE,
                "OTP has expired"
        );
        }

        if(approval.getClient_data() != null){
        try {
            Client client = new ObjectMapper()
                    .readValue(approval.getClient_data(), Client.class);

            clientRepo.save(client);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse client data", e);
        }
    }
        aproval.deleteExpiredOtps();
        aproval.deleteByUsernameAndOtp(username, userInputOtp);

        return true;
    }

}
