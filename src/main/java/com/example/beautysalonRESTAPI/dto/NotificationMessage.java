package com.example.beautysalonRESTAPI.dto;

import lombok.Data;

public class NotificationMessage {
    
    private String token;
    private String title;
    private String body;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
