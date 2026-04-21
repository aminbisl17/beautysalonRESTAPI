package com.example.beautysalonRESTAPI.repository;
public interface EmailSender {
    
    void send(String to, String Subject, String Message);
}