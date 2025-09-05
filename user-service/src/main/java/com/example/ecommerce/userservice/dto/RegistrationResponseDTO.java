package com.example.ecommerce.userservice.dto;

import java.util.Map;

public class RegistrationResponseDTO {
    private String message;
    private Map<String, Object> user;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(String message, Map<String, Object> user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }
}