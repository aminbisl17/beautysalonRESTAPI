package com.example.beautysalonRESTAPI.backend.repository;
public interface EmailSender {
    
    void send(String to, String Subject, String Message);
}