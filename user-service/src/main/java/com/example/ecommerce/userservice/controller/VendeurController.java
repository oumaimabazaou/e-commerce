// Fichier: VendeurController.java (VERSION COMPLÈTE ET FINALE)
package com.example.ecommerce.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/api/vendeurs")
@CrossOrigin(originPatterns = "*")
public class VendeurController {
    private static final Logger logger = LoggerFactory.getLogger(VendeurController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{vendorId}/boutique")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> getBoutiqueByVendorId(@PathVariable Long vendorId) {
        logger.info("Requête autorisée pour récupérer la boutique du vendeur ID : {}", vendorId);
        try {
            String url = "http://boutique-service/api/boutiques/vendeur/" + vendorId;

            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().isEmpty()) {
                logger.warn("Aucune boutique trouvée ou erreur de service pour le vendeur ID : {}", vendorId);
                return ResponseEntity.notFound().build();
            }

            // On prend le premier élément de la liste
            Object boutiqueData = response.getBody().get(0);
            logger.info("Boutique récupérée avec succès pour le vendeur {}", vendorId);

            // On renvoie les données brutes telles qu'on les a reçues
            return ResponseEntity.ok(boutiqueData);

        } catch (Exception e) {
            logger.error("Erreur critique lors de la récupération de la boutique pour le vendeur {} : {}", vendorId, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}