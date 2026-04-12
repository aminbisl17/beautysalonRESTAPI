package com.example.beautysalonRESTAPI.backend.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_FROM_NUMBER}")
    private String fromNumber;


    public String getFromNumber() {
        return fromNumber;
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}