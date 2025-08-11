package com.example.commandeservice.controller;

import com.example.commandeservice.entity.Panier;
import com.example.commandeservice.service.PanierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}