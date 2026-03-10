package com.example.beautysalonRESTAPI.backend.service;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.dto.OtpData;
import com.example.beautysalonRESTAPI.backend.security.Configuration.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

        private Map<String, OtpData> otpMap = new ConcurrentHashMap<>();


    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendSms(String to, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber(to),                  // recipient number
                        new PhoneNumber(twilioConfig.getFromNumber()), // Twilio number
                        messageBody
                ).create();

        return message.getSid();
    }

       public String sendOtp(String phoneNumber, String username) {

        String otp = generateOTP();

        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000);

        otpMap.put(username, new OtpData(otp, expiryTime));

  //      String messageBody = "Your OTP code is: " + otp;

        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioConfig.getFromNumber()),
                "Kodi juaj i verifikimit: " + otp
        ).create();

        return message.getSid();
    }

    public boolean validateOTP(String userInputOtp, String username) {

        OtpData otpData = otpMap.get(username);

        if (otpData == null) {
            throw new IllegalArgumentException("OTP not found");
        }

        if (System.currentTimeMillis() > otpData.getExpiryTime()) {
            otpMap.remove(username);
            throw new IllegalArgumentException("OTP expired");
        }

        if (!otpData.getOtp().equals(userInputOtp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        otpMap.remove(username);
        return true;
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}