
package com.example.beautysalonRESTAPI.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.beautysalonRESTAPI.Configuration.TwilioConfig;
import com.example.beautysalonRESTAPI.dto.OtpData;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dm.sent.client.SentClient;
import dm.sent.client.okhttp.SentOkHttpClient;
import dm.sent.core.JsonValue;
import dm.sent.models.messages.MessageSendParams;
import dm.sent.models.messages.MessageSendResponse;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.security.SecureRandom;

@Service
public class SmsService {

        
 private final SentClient client;


 private static final long OTP_EXPIRATION_MINUTES = 5;

private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
private final Map<String, LocalDateTime> otpExpiration = new ConcurrentHashMap<>();


    private final TwilioConfig twilioConfig;

    private final AprovalsRepository aproval; 

    public SmsService(TwilioConfig twilioConfig, AprovalsRepository aproval,    @Value("${SENT_DM_API_KEY}") String apiKey) {
        this.twilioConfig = twilioConfig;
        this.aproval = aproval;
             this.client = SentOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    
    }

    public String sentMessage(String phoneNumber, String otp) {

        MessageSendParams params = MessageSendParams.builder()
                .addChannel("whatsapp")
                .template(
                    MessageSendParams.Template.builder()
                        .id("6630d08b-1df6-4f83-bf29-5e8a164bc68a")
                        .parameters(
                            MessageSendParams.Template.Parameters.builder()
                                .putAdditionalProperty(
                                    "var_1", JsonValue.from(otp))
                                .build())
                        .build())
                .addTo(phoneNumber)
                .build();

        MessageSendResponse response = client.messages().send(params);

        var data = response.data()
                .orElseThrow(() ->
                    new IllegalStateException("Sent response contains no data"));

        var recipients = data.recipients()
                .orElseThrow(() ->
                    new IllegalStateException("Sent response contains no recipients"));

        if (recipients.isEmpty()) {
            throw new IllegalStateException("Sent response contains an empty recipient list");
        }

        return recipients.get(0).messageId()
                .orElseThrow(() ->
                    new IllegalStateException("Sent response contains no message ID"));
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

public void storeOTP(String phoneNumber, String otp) {
    otpStorage.put(phoneNumber, otp);
    otpExpiration.put(
            phoneNumber,
            LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES)
    );
}

public boolean validateOTP(String phoneNumber, String submittedOtp) {
    String storedOtp = otpStorage.get(phoneNumber);
    LocalDateTime expiresAt = otpExpiration.get(phoneNumber);

    if (storedOtp == null || expiresAt == null) {
        return false;
    }

    if (!LocalDateTime.now().isBefore(expiresAt)) {
        otpStorage.remove(phoneNumber);
        otpExpiration.remove(phoneNumber);
        return false;
    }

    if (!storedOtp.equals(submittedOtp)) {
        return false;
    }

    // Clear both entries after successful validation
    otpStorage.remove(phoneNumber);
    otpExpiration.remove(phoneNumber);

    return true;
}
}


/* 
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

    private final RestClient restClient;
    private final String apiToken;

    public SmsService(
            @Value("${WATI_API_URL}") String apiUrl,
            @Value("${WATI_API_TOKEN}") String apiToken) {

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();

        this.apiToken = apiToken;
    }

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
} */