
/*package com.example.beautysalonRESTAPI.service;

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
}*/


package com.example.beautysalonRESTAPI.service;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.text.DecimalFormat;
@Service
public class SmsService {

    @Value("${WATI_API_URL}")
    private String apiUrl;

    @Value("${WATI_API_TOKEN}")
    private String apiToken;

    private final RestClient restClient = RestClient.builder().build();

    /**
     * Send a normal WATI template message.
     *
     * @param phoneNumber recipient number, e.g. 38344123456
     * @param templateName WATI approved template name
     * @param parameters template parameters
     */
   public String sendMessage(
        String phoneNumber,
        String templateName,
        Map<String, String> parameters) {

    var parameterList = parameters.entrySet()
            .stream()
            .map(entry -> Map.of(
                    "name", entry.getKey(),
                    "value", entry.getValue()
            ))
            .toList();

    Map<String, Object> body = Map.of(
            "template_name", templateName,
            "broadcast_name", "BeautySalon",
            "parameters", parameterList
    );

    return restClient.post()
            .uri(uriBuilder -> uriBuilder
                    .path("/api/v2/sendTemplateMessage")
                    .queryParam("whatsappNumber", phoneNumber)
                    .build())
            .header("Authorization", "Bearer " + apiToken)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .body(String.class);
}


    /**
     * Send OTP through a WATI template.
     */

    
    public String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    public String sendOtp(String phoneNumber, String otp) {

    return sendMessage(
            phoneNumber,
            "default_welcome_v2",
            Map.of(
                    "name", otp
            )
    );
}
}