package com.example.beautysalonRESTAPI.backend.service;
public interface EmailSender {
    
    void send(String to, String Subject, String Message);
}