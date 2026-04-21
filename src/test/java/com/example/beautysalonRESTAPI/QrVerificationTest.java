package com.example.beautysalonRESTAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.beautysalonRESTAPI.api.AuthController;

@SpringBootTest
public class QrVerificationTest {
    
    @MockitoBean
    AuthController auth;

    @Test
    void verify(){

    }
}
