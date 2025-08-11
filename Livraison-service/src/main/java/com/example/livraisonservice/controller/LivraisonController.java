package com.example.livraisonservice.controller;

import com.example.livraisonservice.entity.Livraison;
import com.example.livraisonservice.service.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
public class LivraisonController {
    @Autowired
    private LivraisonService livraisonService;

    @PostMapping
    public ResponseEntity<Livraison> createLivraison(@RequestBody Livraison livraison) {
        return ResponseEntity.ok(livraisonService.createLivraison(livraison));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Integer id) {
        return livraisonService.getLivraisonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/commande/{idCommande}")
    public ResponseEntity<List<Livraison>> getLivraisonsByCommande(@PathVariable Integer idCommande) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByCommande(idCommande));
    }

    @GetMapping("/livreur/{idLivreur}")
    public ResponseEntity<List<Livraison>> getLivraisonsByLivreur(@PathVariable String idLivreur) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByLivreur(idLivreur));
    }

    @GetMapping("/entreprise/{idEntreprise}")
    public ResponseEntity<List<Livraison>> getLivraisonsByEntreprise(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(livraisonService.getLivraisonsByEntreprise(idEntreprise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> updateLivraison(@PathVariable Integer id, @RequestBody Livraison livraison) {
        try {
            if (livraison == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(livraisonService.updateLivraison(id, livraison));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Si l'entité n'est pas trouvée
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Integer id) {
        livraisonService.deleteLivraison(id);
        return ResponseEntity.noContent().build();
    }
}