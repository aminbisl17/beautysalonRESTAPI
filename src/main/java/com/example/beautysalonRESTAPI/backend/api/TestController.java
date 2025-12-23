package com.example.beautysalonRESTAPI.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.security.JwtUtil;

@RestController
@RequestMapping("/test")
public class TestController {

    private final JwtUtil jwtUtil;

    public TestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

      @Autowired
 private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/generate-token")
    public String generateToken() {
        String token = jwtUtil.generateToken("admin123", "ADMIN");
        System.out.println("Generated Token: " + token); // prints to console
        return ""; // returns token in response for testing
    }

    @GetMapping("/test-token")
    public void testToken(){
         System.out.println("New pEncoder: " + passwordEncoder.encode("admin123"));
       //  System.out.println(("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEyM0EiLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NjUxMTgxNTQsImV4cCI6MTc2NTE1NDE1NH0.xTz3sQkaCxz3YLNXFSC_eSfBiCFZhvruUQNzv1fK620".equals(passwordEncoder.encode("admin123A")))); 
    }
}