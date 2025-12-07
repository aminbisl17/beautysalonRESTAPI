package com.example.beautysalonRESTAPI.backend.security.Configuration;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private final Dotenv dotenv;

    public MailConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("MAIL_HOST"));
        mailSender.setUsername(dotenv.get("MAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("MAIL_PASSWORD"));
        mailSender.setPort(587); // Gmail SMTP port
        mailSender.setProtocol("smtp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}