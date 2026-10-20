package com.example.beautysalonRESTAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.beautysalonRESTAPI.Configuration.FirebaseConfig;
import com.example.beautysalonRESTAPI.dto.NotificationMessage;
import com.google.api.services.storage.model.Notification;
import com.google.firebase.messaging.FirebaseMessaging;

@Service
public class FirebaseService {
    

    @Autowired
    FirebaseMessaging firebaseMessaging;

  //  public String token;

  
public String sendExpoNotification(String token, String title, String body) {

    String url = "https://exp.host/--/api/v2/push/send";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String json = """
    {
      "to": "%s",
      "title": "%s",
      "body": "%s",
      "sound": "default"
    }
    """.formatted(token, title, body);

    HttpEntity<String> request = new HttpEntity<>(json, headers);

    String response = restTemplate.postForObject(url, request, String.class);

    System.out.println("Expo response: " + response);
   return response;
}
}
