package com.example.beautysalonRESTAPI.api;

import org.springframework.web.bind.annotation.RestController;

import com.twilio.http.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/server")
public class Server {
    

      @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok().build();
  
    }
}
