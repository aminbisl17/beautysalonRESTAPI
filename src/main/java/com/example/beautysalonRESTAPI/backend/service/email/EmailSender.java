package com.example.beautysalonRESTAPI.backend.service.email;
public interface EmailSender {
    
    void send(String to, String Subject, String Message);
}