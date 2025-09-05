package com.example.commandeservice.entity;

import lombok.Data;

@Data
public class CalculRequest {
    private double subtotal;
    private boolean premium;
    private boolean local;
    private String clientId;
    private String boutique;
    
    // Ajout explicite des getters et setters pour résoudre le problème de compilation
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBoutique() {
        return boutique;
    }

    public void setBoutique(String boutique) {
        this.boutique = boutique;
    }
}