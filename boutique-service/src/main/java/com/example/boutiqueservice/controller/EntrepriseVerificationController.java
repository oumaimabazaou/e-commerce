package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.EntrepriseVerification;
import com.example.boutiqueservice.service.EntrepriseVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@RestController
@RequestMapping("/api/entreprise-verifications")
public class EntrepriseVerificationController {
    @Autowired
    private EntrepriseVerificationService entrepriseVerificationService;

    @PostMapping
    public ResponseEntity<EntrepriseVerification> createEntrepriseVerification(@RequestBody EntrepriseVerification entrepriseVerification) {
        try {
            if (entrepriseVerification == null || entrepriseVerification.getNom() == null || entrepriseVerification.getNom().trim().isEmpty() ||
                    entrepriseVerification.getAdresse() == null || entrepriseVerification.getAdresse().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(entrepriseVerificationService.createEntrepriseVerification(entrepriseVerification));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body(null); // Conflit de données (ex. doublon)
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Autres erreurs
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrepriseVerification> getEntrepriseVerificationById(@PathVariable Integer id) {
        return entrepriseVerificationService.getEntrepriseVerificationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EntrepriseVerification>> getAllEntrepriseVerifications() {
        return ResponseEntity.ok(entrepriseVerificationService.getAllEntrepriseVerifications());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrepriseVerification> updateEntrepriseVerification(@PathVariable Integer id, @RequestBody EntrepriseVerification details) {
        try {
            return ResponseEntity.ok(entrepriseVerificationService.updateEntrepriseVerification(id, details));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepriseVerification(@PathVariable Integer id) {
        try {
            entrepriseVerificationService.deleteEntrepriseVerification(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}