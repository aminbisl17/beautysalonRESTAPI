package com.example.beautysalonRESTAPI.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;

import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class ApprovalService {
    
    
    private final AprovalsRepository aproval; 

    ApprovalService(AprovalsRepository aproval){
        this.aproval = aproval;
    }

     //   @Transactional
    public void createOtp(String otp, String username, Client client) {

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
}
