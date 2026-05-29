package com.example.beautysalonRESTAPI.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.Configuration.TwilioConfig;
import com.example.beautysalonRESTAPI.dto.OtpData;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

    private final AprovalsRepository aproval; 

    public SmsService(TwilioConfig twilioConfig, AprovalsRepository aproval) {
        this.twilioConfig = twilioConfig;
        this.aproval = aproval;
    
    }

    @Autowired
    private ApprovalService approvalService;

    public String sendSms(String to, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber(to),                  // recipient number
                        new PhoneNumber(twilioConfig.getFromNumber()), // Twilio number
                        messageBody
                ).create();

        return message.getSid();
    }

      public String sendOtp(String phoneNumber, String otp) {
    Message message = Message.creator(
        new PhoneNumber(phoneNumber),
        new PhoneNumber(twilioConfig.getFromNumber()),
        "Kodi juaj i verifikimit: " + otp 
    ).create();

    return message.getSid();
}

    public String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}