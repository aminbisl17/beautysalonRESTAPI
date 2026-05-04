package com.example.beautysalonRESTAPI.Configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FirebaseConfig {
    
    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException{
          GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
              new ClassPathResource("beauty-salon-9b57e-firebase-adminsdk-fbsvc-1e19cb30d7.json").getInputStream());
              FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                 .setCredentials(googleCredentials).build();
                 FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "Beauty-Salon");
                 return FirebaseMessaging.getInstance(app);
    }
}
