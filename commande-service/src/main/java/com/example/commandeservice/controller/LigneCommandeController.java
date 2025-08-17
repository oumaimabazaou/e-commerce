package com.example.commandeservice.controller;

import com.example.commandeservice.entity.LigneCommande;
import com.example.commandeservice.service.LigneCommandeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ligne-commandes")
public class LigneCommandeController {
    @Autowired
    private LigneCommandeService ligneCommandeService;

    @PostMapping
    public ResponseEntity<LigneCommande> createLigneCommande(@Valid @RequestBody LigneCommande ligneCommande) {
        return ResponseEntity.ok(ligneCommandeService.createLigneCommande(ligneCommande));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LigneCommande>> getLigneCommandeById(@PathVariable Integer id) {
        return ResponseEntity.ok(ligneCommandeService.getLigneCommandeById(id));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommande>> getLigneCommandesByCommande(@PathVariable Integer commandeId) {
        return ResponseEntity.ok(ligneCommandeService.getLigneCommandesByCommande(commandeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneCommande> updateLigneCommande(@PathVariable Integer id, @Valid @RequestBody LigneCommande ligneCommande) {
        return ResponseEntity.ok(ligneCommandeService.updateLigneCommande(id, ligneCommande));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Integer id) {
        ligneCommandeService.deleteLigneCommande(id);
        return ResponseEntity.noContent().build();
    }
}