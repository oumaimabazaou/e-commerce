package com.example.commandeservice.entity;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CalculResponse {
    private double subtotal;
    private double shippingFee;
    private double tva;
    private double total;
    private boolean isPremium;
    private String message;

    public CalculResponse(double subtotal, double shippingFee, double tva, double total) {
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.tva = tva;
        this.total = total;
        this.isPremium = false;
        this.message = "Calcul effectué avec succès";
    }
    
    // Ajout explicite des getters et setters pour résoudre le problème de compilation
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}