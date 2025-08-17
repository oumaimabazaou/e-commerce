package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Publicite;
import com.example.boutiqueservice.service.PubliciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/publicites")
public class PubliciteController {
    @Autowired
    private PubliciteService publiciteService;

    @PostMapping
    public ResponseEntity<Publicite> createPublicite(@RequestBody Publicite publicite) {
        try {
            if (publicite == null || publicite.getIdBoutique() == null || publicite.getTitre() == null || publicite.getTitre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            // Validation basique du champ JSON
            if (publicite.getStatistiques() != null && !isValidJson(publicite.getStatistiques())) {
                return ResponseEntity.badRequest().body(null);
            }
            // Vérification des dates
            if (publicite.getDateDebut() == null || publicite.getDateFin() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            if (publicite.getDateDebut().isAfter(publicite.getDateFin())) {
                return ResponseEntity.badRequest().body(null);
            }
            // Vérification que idBoutique est positif
            if (publicite.getIdBoutique() <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(publiciteService.createPublicite(publicite));
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
    public ResponseEntity<Publicite> getPubliciteById(@PathVariable Integer id) {
        return publiciteService.getPubliciteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Publicite>> getPublicitesByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(publiciteService.getPublicitesByBoutique(idBoutique));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicite> updatePublicite(@PathVariable Integer id, @RequestBody Publicite publiciteDetails) {
        try {
            return ResponseEntity.ok(publiciteService.updatePublicite(id, publiciteDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicite(@PathVariable Integer id) {
        try {
            publiciteService.deletePublicite(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}