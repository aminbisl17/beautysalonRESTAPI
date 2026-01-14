 package com.example.beautysalonRESTAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import io.github.cdimascio.dotenv.Dotenv;
@SpringBootApplication
public class BeautysalonRestapiApplication {


	public static void main(String[] args) {

       Dotenv dotenv = Dotenv.load();
	

         System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));

       // System.setProperty("twilio.accountSid", dotenv.get("twilio.accountSid"));
        //System.setProperty("twilio.authToken", dotenv.get("twilio.authToken"));
        //System.setProperty("twilio.fromNumber", dotenv.get("twilio.fromNumber"));

            System.setProperty("TWILIO_ACCOUNT_SID", dotenv.get("TWILIO_ACCOUNT_SID"));
    System.setProperty("TWILIO_AUTH_TOKEN", dotenv.get("TWILIO_AUTH_TOKEN"));
    System.setProperty("TWILIO_FROM_NUMBER", dotenv.get("TWILIO_FROM_NUMBER"));
        
		SpringApplication.run(BeautysalonRestapiApplication.class, args);
	}

}