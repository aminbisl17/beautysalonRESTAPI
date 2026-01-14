package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.security.Configuration.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendSms(String to, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber(to),                  // recipient number
                        new PhoneNumber(twilioConfig.getFromNumber()), // Twilio number
                        messageBody
                ).create();

        return message.getSid(); // returns unique message SID
    }
}