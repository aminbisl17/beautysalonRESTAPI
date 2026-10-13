package com.example.beautysalonRESTAPI.api;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/whatsapp")
public class WhatsAppWebhookController {

    @Value("${whatsapp.webhook-verify-token}")
    private String verifyToken;

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode)
                && verifyToken.equals(token)) {
            return ResponseEntity.ok(challenge);
        }

        return ResponseEntity.status(403).body("Forbidden");
    }

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(
            @RequestBody String payload) {

        // Process incoming messages and status updates here.
        System.out.println("WhatsApp webhook received");

        return ResponseEntity.ok().build();
    }
}
