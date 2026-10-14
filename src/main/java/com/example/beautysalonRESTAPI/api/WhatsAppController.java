
package com.example.beautysalonRESTAPI.api;

import com.example.beautysalonRESTAPI.service.SmsService;
import com.example.beautysalonRESTAPI.service.WhatsAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

      @Autowired 
      private SmsService smsService;


    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

      @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage() {

        String messageId = smsService.sentMessage("+38345380871", "433432");

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "messageId", messageId
        ));
    }

    @PostMapping
    public ResponseEntity<?> sendTestMessage() {
        try {
            String result = whatsAppService.sendTextMessage(
                    "38345380871",
                    "Hello! This is a test from my BeautySalon Spring Boot application."
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "metaResponse", result
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }
}

