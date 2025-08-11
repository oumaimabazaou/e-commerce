package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Promotion;
import com.example.boutiqueservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        try {
            if (promotion == null || promotion.getIdBoutique() == null || promotion.getNom() == null || promotion.getNom().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            // Validation basique des champs JSON
            if ((promotion.getProduitsEligibles() != null && !isValidJson(promotion.getProduitsEligibles())) ||
                    (promotion.getConditionsUtilisation() != null && !isValidJson(promotion.getConditionsUtilisation()))) {
                return ResponseEntity.badRequest().body(null);
            }
            // Vérification supplémentaire
            if (promotion.getPourcentage() == null) {
                return ResponseEntity.badRequest().body(null); // Pourcentage requis
            }
            return ResponseEntity.ok(promotionService.createPromotion(promotion));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body(null); // Conflit de données
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(400).body(null); // Violation de contrainte
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Autres erreurs
        }
    }

    // Méthode utilitaire pour valider un format JSON simple
    private boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) return true; // Accepte null ou vide
        try {
            if (json.startsWith("[") && json.endsWith("]")) return true; // Tableau
            if (json.startsWith("{") && json.endsWith("}")) return true; // Objet
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Integer id) {
        return promotionService.getPromotionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Promotion>> getPromotionsByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(promotionService.getPromotionsByBoutique(idBoutique));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Integer id, @RequestBody Promotion promotionDetails) {
        try {
            return ResponseEntity.ok(promotionService.updatePromotion(id, promotionDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        try {
            promotionService.deletePromotion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}