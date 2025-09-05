package com.example.paiementabonnementservice.controller;

import com.example.paiementabonnementservice.entity.Paiement;
import com.example.paiementabonnementservice.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    // ✅ ENDPOINT POUR LES ABONNEMENTS (méthode originale)
    @PostMapping("/abonnement")
    public ResponseEntity<Paiement> createPaiementAbonnement(@RequestBody Paiement paiement) {
        try {
            Paiement savedPaiement = paiementService.createPaiement(paiement);
            return ResponseEntity.ok(savedPaiement);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ ENDPOINT POUR LES COMMANDES (nouveau - compatible frontend)
    @PostMapping("/commande")
    public ResponseEntity<Map<String, Object>> processCommandePayment(@RequestBody Map<String, Object> paymentData) {
        try {
            // Extraire les données du paiement
            String orderId = paymentData.get("orderId") != null ? paymentData.get("orderId").toString() : null;
            Double amount = null;
            String paymentMethod = paymentData.get("paymentMethod") != null ? paymentData.get("paymentMethod").toString() : "UNKNOWN";
            String currency = paymentData.get("currency") != null ? paymentData.get("currency").toString() : "EUR";

            // Gestion sécurisée du montant
            if (paymentData.get("amount") != null) {
                if (paymentData.get("amount") instanceof Number) {
                    amount = ((Number) paymentData.get("amount")).doubleValue();
                } else {
                    amount = Double.parseDouble(paymentData.get("amount").toString());
                }
            }

            // Validation des données requises
            if (orderId == null || orderId.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "orderId est requis");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            if (amount == null || amount <= 0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "montant doit être positif");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Créer un paiement pour la commande
            Paiement paiement = new Paiement();
            paiement.setIdAbonnement(Integer.parseInt(orderId)); // Utiliser orderId comme référence
            paiement.setMontant(amount);
            paiement.setMethodePaiement(paymentMethod);

            // Sauvegarder le paiement
            Paiement savedPaiement = paiementService.createPaiement(paiement);

            // Réponse compatible avec le frontend
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);

            Map<String, Object> data = new HashMap<>();
            data.put("paymentId", savedPaiement.getId());
            data.put("status", "completed");
            data.put("method", paymentMethod);
            data.put("amount", amount);
            data.put("currency", currency);
            data.put("orderId", orderId);
            data.put("transactionId", "TXN-" + System.currentTimeMillis());

            response.put("data", data);
            response.put("message", "Paiement traité avec succès");

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Format de données invalide: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Données invalides: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors du traitement du paiement: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // ✅ ENDPOINT GÉNÉRIQUE POUR TOUS LES PAIEMENTS (fallback)
    @PostMapping
    public ResponseEntity<Map<String, Object>> processGenericPayment(@RequestBody Map<String, Object> paymentData) {
        // Rediriger vers l'endpoint spécifique selon le type
        if (paymentData.containsKey("orderId")) {
            return processCommandePayment(paymentData);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Type de paiement non reconnu. Utilisez /commande ou /abonnement");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // ✅ RÉCUPÉRER UN PAIEMENT PAR ID
    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Integer id) {
        try {
            return paiementService.getPaiementById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ RÉCUPÉRER TOUS LES PAIEMENTS
    @GetMapping
    public ResponseEntity<List<Paiement>> getAllPaiements() {
        try {
            List<Paiement> paiements = paiementService.getAllPaiements();
            return ResponseEntity.ok(paiements);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ RÉCUPÉRER LES PAIEMENTS PAR ABONNEMENT
    @GetMapping("/abonnement/{idAbonnement}")
    public ResponseEntity<List<Paiement>> getPaiementsByAbonnement(@PathVariable Integer idAbonnement) {
        try {
            List<Paiement> paiements = paiementService.getPaiementsByAbonnement(idAbonnement);
            return ResponseEntity.ok(paiements);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ RÉCUPÉRER LES PAIEMENTS PAR COMMANDE
    @GetMapping("/commande/{orderId}")
    public ResponseEntity<List<Paiement>> getPaiementsByCommande(@PathVariable Integer orderId) {
        try {
            List<Paiement> paiements = paiementService.getPaiementsByAbonnement(orderId);
            return ResponseEntity.ok(paiements);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ ENDPOINT DE SANTÉ
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "paiementabonnement-service");
        health.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(health);
    }

    // ✅ MARQUER UN PAIEMENT COMME PAYÉ (pour compatibilité)
    @PostMapping("/commandes/{idCommande}/pay")
    public ResponseEntity<Map<String, Object>> markPayment(@PathVariable String idCommande) {
        try {
            paiementService.markPayment(idCommande);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paiement marqué pour la commande " + idCommande);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors du marquage du paiement: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}