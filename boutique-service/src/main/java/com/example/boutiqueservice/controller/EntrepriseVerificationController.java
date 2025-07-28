package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.EntrepriseVerification;
import com.example.boutiqueservice.service.EntrepriseVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entreprise-verifications")
public class EntrepriseVerificationController {
    @Autowired
    private EntrepriseVerificationService entrepriseVerificationService;

    @PostMapping
    public ResponseEntity<EntrepriseVerification> createEntrepriseVerification(@RequestBody EntrepriseVerification entrepriseVerification) {
        return ResponseEntity.ok(entrepriseVerificationService.createEntrepriseVerification(entrepriseVerification));
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
        return ResponseEntity.ok(entrepriseVerificationService.updateEntrepriseVerification(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepriseVerification(@PathVariable Integer id) {
        entrepriseVerificationService.deleteEntrepriseVerification(id);
        return ResponseEntity.noContent().build();
    }
}