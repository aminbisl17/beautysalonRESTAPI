package com.example.beautysalonRESTAPI.backend.api;

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

    @GetMapping("/generate-token")
    public String generateToken() {
        String token = jwtUtil.generateToken("admin", "ADMIN");
        System.out.println("Generated Token: " + token); // prints to console
        return ""; // returns token in response for testing
    }
}