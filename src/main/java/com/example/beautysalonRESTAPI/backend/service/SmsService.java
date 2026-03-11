package com.example.beautysalonRESTAPI.backend.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.dto.OtpData;
import com.example.beautysalonRESTAPI.backend.model.Aprovals;
import com.example.beautysalonRESTAPI.backend.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.backend.security.Configuration.TwilioConfig;
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

     public boolean validateOTP(String userInputOtp, String username) {
        Aprovals approval = aproval.findByUsername(username).orElse(null);

        if (approval == null) {
            throw new IllegalArgumentException("OTP not found");
        }
        if (approval.getCreated().plusMinutes(5).isBefore(LocalDateTime.now())) {
            aproval.delete(approval); // remove expired row
            throw new IllegalArgumentException("OTP expired");
        }

        if (approval.getOtp() != Integer.parseInt(userInputOtp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        aproval.delete(approval);

        return true;
    }

    public String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}