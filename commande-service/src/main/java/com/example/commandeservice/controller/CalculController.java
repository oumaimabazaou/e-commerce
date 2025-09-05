package com.example.commandeservice.controller;

import com.example.commandeservice.entity.CalculRequest;
import com.example.commandeservice.entity.CalculResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculs")
public class CalculController {

    @PostMapping("/livraison")
    public ResponseEntity<CalculResponse> calculateShipping(@RequestBody CalculRequest request) {
        double subtotal = request.getSubtotal();
        boolean isPremium = request.isPremium();
        boolean isLocal = request.isLocal();

        // Calcul frais de livraison
        double shippingFee = 0;
        if (!isPremium) { // Premium = livraison gratuite
            if (isLocal) {
                shippingFee = subtotal * 0.04; // x4 = 4% du prix
            } else {
                shippingFee = subtotal * 0.08; // x8 = 8% du prix
            }
        }

        // TVA 20%
        double tva = subtotal * 0.20;
        double total = subtotal + shippingFee + tva;

        return ResponseEntity.ok(new CalculResponse(subtotal, shippingFee, tva, total));
    }
}