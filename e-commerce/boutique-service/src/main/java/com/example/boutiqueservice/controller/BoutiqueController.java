package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Boutique;
import com.example.boutiqueservice.service.BoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boutiques")
public class BoutiqueController {
    @Autowired
    private BoutiqueService boutiqueService;

    @PostMapping
    public ResponseEntity<Boutique> createBoutique(@RequestBody Boutique boutique) {
        return ResponseEntity.ok(boutiqueService.createBoutique(boutique));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boutique> getBoutiqueById(@PathVariable Integer id) {
        return boutiqueService.getBoutiqueById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/vendeur/{idVendeur}")
    public ResponseEntity<List<Boutique>> getBoutiquesByVendeur(@PathVariable String idVendeur) {
        return ResponseEntity.ok(boutiqueService.getBoutiquesByVendeur(idVendeur));
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Boutique>> getActiveBoutiquesByVille(@PathVariable String ville) {
        return ResponseEntity.ok(boutiqueService.getActiveBoutiquesByVille(ville));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boutique> updateBoutique(@PathVariable Integer id, @RequestBody Boutique boutiqueDetails) {
        return ResponseEntity.ok(boutiqueService.updateBoutique(id, boutiqueDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoutique(@PathVariable Integer id) {
        boutiqueService.deleteBoutique(id);
        return ResponseEntity.noContent().build();
    }
}