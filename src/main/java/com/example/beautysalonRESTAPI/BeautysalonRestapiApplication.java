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
        
		SpringApplication.run(BeautysalonRestapiApplication.class, args);
	}

}
