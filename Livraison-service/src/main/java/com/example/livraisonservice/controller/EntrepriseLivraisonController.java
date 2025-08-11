package com.example.livraisonservice.controller;

import com.example.livraisonservice.entity.EntrepriseLivraison;
import com.example.livraisonservice.service.EntrepriseLivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entreprises-livraison")
public class EntrepriseLivraisonController {
    @Autowired
    private EntrepriseLivraisonService entrepriseLivraisonService;

    @PostMapping
    public ResponseEntity<EntrepriseLivraison> createEntrepriseLivraison(@RequestBody EntrepriseLivraison entreprise) {
        try {
            if (entreprise == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(entrepriseLivraisonService.createEntrepriseLivraison(entreprise));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrepriseLivraison> getEntrepriseLivraisonById(@PathVariable Integer id) {
        return entrepriseLivraisonService.getEntrepriseLivraisonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EntrepriseLivraison>> getAllEntreprisesLivraison() {
        return ResponseEntity.ok(entrepriseLivraisonService.getAllEntreprisesLivraison());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrepriseLivraison> updateEntrepriseLivraison(@PathVariable Integer id, @RequestBody EntrepriseLivraison entreprise) {
        try {
            if (entreprise == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(entrepriseLivraisonService.updateEntrepriseLivraison(id, entreprise));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Si l'entité n'est pas trouvée
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepriseLivraison(@PathVariable Integer id) {
        entrepriseLivraisonService.deleteEntrepriseLivraison(id);
        return ResponseEntity.noContent().build();
    }
}