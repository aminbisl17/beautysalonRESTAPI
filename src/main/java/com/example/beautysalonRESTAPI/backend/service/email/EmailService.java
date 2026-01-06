package com.example.beautysalonRESTAPI.backend.service.email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
@Service
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
            this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String Subject, String Message) {
        SimpleMailMessage sms = new SimpleMailMessage();
           sms.setFrom("dhshcnbshc@gmail.com");
           sms.setTo(to);
           sms.setSubject(Subject);
           sms.setText(Message);
           this.mailSender.send(sms);
    }
}