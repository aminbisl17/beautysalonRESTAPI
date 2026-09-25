/*package com.example.beautysalonRESTAPI.api.Company.Technical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.NotificationMessage;
import com.example.beautysalonRESTAPI.dto.TokenRequest;
import com.example.beautysalonRESTAPI.service.FirebaseService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    FirebaseService firebaseService;
    String token;

    @PostMapping("/save-token")
public void saveToken(@RequestBody TokenRequest request) {
    System.out.println("Expo token: " + request.getToken());
    //   token = request.getToken();
}

@PostMapping("/send")
public String send(@RequestBody NotificationMessage msg) {
    return firebaseService.sendExpoNotification(
            msg.getToken(),
            msg.getTitle(),
            msg.getBody()
    );
}
}
*/
