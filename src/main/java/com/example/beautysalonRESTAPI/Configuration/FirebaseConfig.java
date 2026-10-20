/*ackage com.example.beautysalonRESTAPI.Configuration;

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

*/

package com.example.beautysalonRESTAPI.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    public FirebaseMessaging firebaseMessaging() throws IOException {

        String firebaseJson =
                System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");

        InputStream inputStream;

        if (firebaseJson != null && !firebaseJson.isBlank()) {
            // Azure: load credentials from environment variable
            inputStream = new ByteArrayInputStream(
                    firebaseJson.getBytes(StandardCharsets.UTF_8));
        } else {
            // Local development: load ignored JSON from resources
            inputStream = new ClassPathResource(
                    "beauty-salon-9b57e-firebase-adminsdk-fbsvc-5f3c32811b.json"
            ).getInputStream();
        }

        try (InputStream credentialsStream = inputStream) {
            GoogleCredentials credentials =
                    GoogleCredentials.fromStream(credentialsStream);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp app;

            synchronized (FirebaseApp.class) {
                if (FirebaseApp.getApps().stream()
                        .anyMatch(existing ->
                                existing.getName().equals("Beauty-Salon"))) {
                    app = FirebaseApp.getInstance("Beauty-Salon");
                } else {
                    app = FirebaseApp.initializeApp(
                            options, "Beauty-Salon");
                }
            }

            return FirebaseMessaging.getInstance(app);
        }
    }
}