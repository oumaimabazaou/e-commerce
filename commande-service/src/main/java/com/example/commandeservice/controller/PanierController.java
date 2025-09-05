package com.example.commandeservice.controller;

import com.example.commandeservice.entity.Panier;
import com.example.commandeservice.service.PanierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {
    @Autowired
    private PanierService panierService;

    @PostMapping
    public ResponseEntity<Panier> addToPanier(@Valid @RequestBody Panier panier) {
        return ResponseEntity.ok(panierService.addToPanier(panier));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Panier>> getPanierById(@PathVariable Integer id) {
        return ResponseEntity.ok(panierService.getPanierById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Panier>> getPaniersByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(panierService.getPaniersByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Panier> updatePanier(@PathVariable Integer id, @Valid @RequestBody Panier panier) {
        return ResponseEntity.ok(panierService.updatePanier(id, panier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromPanier(@PathVariable Integer id) {
        panierService.removeFromPanier(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<Void> clearPanier(@PathVariable String clientId) {
        panierService.clearPanier(clientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouterAuPanier(@RequestBody Map<String, Object> request) {
        try {
            String idClient = (String) request.get("idClient");
            Integer idProduit = (Integer) request.get("idProduit");
            Integer quantite = (Integer) request.get("quantite");

            // ✅ VÉRIFIER QUE LES CHAMPS OBLIGATOIRES EXISTENT
            if (idClient == null || idProduit == null || quantite == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Champs obligatoires manquants: idClient, idProduit, quantite"
                ));
            }

            Panier panier = new Panier();
            panier.setIdClient(idClient);
            panier.setIdProduit(idProduit);
            panier.setQuantite(quantite);

            // ✅ GÉRER LES CHAMPS OPTIONNELS AVEC VALEURS PAR DÉFAUT
            Object nomProduitObj = request.get("nomProduit");
            panier.setNomProduit(nomProduitObj != null ? (String) nomProduitObj : "Produit " + idProduit);

            Object prixObj = request.get("prix");
            panier.setPrix(prixObj != null ? ((Number) prixObj).doubleValue() : 0.0);

            Object imageObj = request.get("imageUrlrecoit");
            panier.setImageUrlrecoit(imageObj != null ? (String) imageObj : "");

            Object categorieObj = request.get("categorie");
            panier.setCategorie(categorieObj != null ? (String) categorieObj : "General");

            Panier savedPanier = panierService.addToPanier(panier);
            return ResponseEntity.ok(Map.of("success", true, "data", savedPanier, "message", "Produit ajouté au panier"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @GetMapping("/client/{clientId}/enrichi")
    public ResponseEntity<Map<String, Object>> getPanierEnrichiByClient(@PathVariable String clientId) {
        try {
            List<Panier> paniers = panierService.getPaniersByClient(clientId);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", paniers,
                    "message", "Panier récupéré avec données enrichies"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Erreur: " + e.getMessage()
            ));
        }
    }
    @GetMapping
    public ResponseEntity<List<Panier>> getAllPaniers() {
        List<Panier> paniers = panierService.getAllPaniers();
        return ResponseEntity.ok(paniers);
    }
}