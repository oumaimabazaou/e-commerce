package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Produit;
import com.example.boutiqueservice.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        try {
            if (produit == null || produit.getIdBoutique() == null || produit.getIdCategorie() == null ||
                    produit.getIdCategorie().trim().isEmpty() || produit.getNomProduit() == null || produit.getNomProduit().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            // Validation des champs JSON
            if ((produit.getPhotos() != null && !isValidJson(produit.getPhotos())) ||
                    (produit.getVideos() != null && !isValidJson(produit.getVideos())) ||
                    (produit.getDimensions() != null && !isValidJson(produit.getDimensions()))) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(produitService.createProduit(produit));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body(null); // Conflit de données
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(400).body(null); // Violation de contrainte
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Autres erreurs
        }
    }

    // Méthode utilitaire améliorée pour valider JSON
    private boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) return true; // Accepte null ou vide
        try {
            // Vérification basique des délimiteurs JSON
            if (json.startsWith("[") && json.endsWith("]")) return true; // Tableau
            if (json.startsWith("{") && json.endsWith("}")) return true; // Objet
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Integer id) {
        return produitService.getProduitById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Produit>> getProduitsByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(produitService.getProduitsByBoutique(idBoutique));
    }

    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(@PathVariable String idCategorie) {
        return ResponseEntity.ok(produitService.getProduitsByCategorie(idCategorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Integer id, @RequestBody Produit produitDetails) {
        try {
            return ResponseEntity.ok(produitService.updateProduit(id, produitDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Integer id) {
        try {
            produitService.deleteProduit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}