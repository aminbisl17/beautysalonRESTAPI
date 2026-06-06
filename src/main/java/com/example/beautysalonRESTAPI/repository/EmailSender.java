package com.example.beautysalonRESTAPI.repository;

import org.springframework.stereotype.Repository;

import jakarta.mail.MessagingException;

@Repository
public interface EmailSender {
    
    void send(String to, String Subject, String Message) throws MessagingException;
}