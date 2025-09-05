package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Boutique;
import com.example.boutiqueservice.service.BoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boutiques")
public class BoutiqueController {

    private static final Logger logger = LoggerFactory.getLogger(BoutiqueController.class);

    @Autowired
    private BoutiqueService boutiqueService;

    @PostMapping
    public ResponseEntity<?> createBoutique(@RequestBody Boutique boutique) {
        try {
            // Validation des données requises
            if (boutique.getIdVendeur() == null) {
                logger.error("ID vendeur manquant dans la requête de création de boutique");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "L'ID du vendeur est obligatoire", "code", "MISSING_VENDOR_ID"));
            }

            if (boutique.getNomBoutique() == null || boutique.getNomBoutique().trim().isEmpty()) {
                logger.error("Nom de boutique manquant");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Le nom de la boutique est obligatoire", "code", "MISSING_SHOP_NAME"));
            }

            if (boutique.getAdresse() == null || boutique.getAdresse().trim().isEmpty()) {
                logger.error("Adresse de boutique manquante");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "L'adresse de la boutique est obligatoire", "code", "MISSING_ADDRESS"));
            }

            logger.info("Création boutique pour vendeur ID: {} - Nom: {}",
                    boutique.getIdVendeur(), boutique.getNomBoutique());

            Boutique savedBoutique = boutiqueService.createBoutique(boutique);

            logger.info("Boutique créée avec succès - ID: {}", savedBoutique.getIdBoutique());

            return ResponseEntity.ok(savedBoutique);

        } catch (IllegalStateException e) {
            logger.warn("Erreur validation création boutique: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage(), "code", "VALIDATION_ERROR"));
        } catch (Exception e) {
            logger.error("Erreur interne création boutique: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Erreur interne du serveur", "code", "INTERNAL_ERROR"));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Boutique> getBoutiqueById(@PathVariable Integer id) {
        return boutiqueService.getBoutiqueById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/vendeur/{idVendeur}")
    public ResponseEntity<List<Boutique>> getBoutiquesByVendeur(@PathVariable Long idVendeur) {
        logger.info("Recherche de la boutique pour le vendeur ID : {}", idVendeur);
        List<Boutique> boutiques = boutiqueService.getBoutiquesByVendeur(idVendeur);
        if (boutiques.isEmpty()) {
            logger.warn("Aucune boutique trouvée pour le vendeur ID : {}", idVendeur);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boutiques);
    }
    @GetMapping
    public ResponseEntity<List<Boutique>> getAllBoutiques() {
        List<Boutique> boutiques = boutiqueService.getAllBoutiques();
        return ResponseEntity.ok(boutiques);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Boutique>> getActiveBoutiques() {
        List<Boutique> boutiques = boutiqueService.getActiveBoutiques();
        return ResponseEntity.ok(boutiques);
    }
    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Boutique>> getActiveBoutiquesByVille(@PathVariable String ville) {
        return ResponseEntity.ok(boutiqueService.getActiveBoutiquesByVille(ville));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boutique> updateBoutique(@PathVariable Integer id, @RequestBody Boutique boutiqueDetails) {
        try {
            Boutique updatedBoutique = boutiqueService.updateBoutique(id, boutiqueDetails);
            return ResponseEntity.ok(updatedBoutique);
        } catch (RuntimeException e) {
            // Gère le cas où la boutique n'est pas trouvée
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoutique(@PathVariable Integer id) {
        boutiqueService.deleteBoutique(id);
        return ResponseEntity.noContent().build();
    }
}