package com.example.beautysalonRESTAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class WhatsAppService {

    private final RestClient restClient;
    private final String phoneNumberId;

    public WhatsAppService(
            @Value("${whatsapp.api-version}") String apiVersion,
            @Value("${whatsapp.phone-number-id}") String phoneNumberId,
            @Value("${whatsapp.access-token}") String accessToken) {

        this.phoneNumberId = phoneNumberId;

        this.restClient = RestClient.builder()
                .baseUrl("https://graph.facebook.com/" + apiVersion)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
    }

    public String sendTextMessage(String recipient, String message) {
        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "recipient_type", "individual",
                "to", recipient,
                "type", "text",
                "text", Map.of(
                        "preview_url", false,
                        "body", message
                )
        );

        return restClient.post()
                .uri("/{phoneNumberId}/messages", phoneNumberId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);
    }
}
