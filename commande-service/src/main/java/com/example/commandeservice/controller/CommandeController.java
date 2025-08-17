package com.example.commandeservice.controller;

import com.example.commandeservice.entity.Commande;
import com.example.commandeservice.service.CommandeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @PostMapping
    public ResponseEntity<?> createCommande(@Valid @RequestBody Commande commande) {
        try {
            Commande savedCommande = commandeService.createCommande(commande);
            return ResponseEntity.ok(savedCommande);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création : " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Commande>> getCommandeById(@PathVariable Integer id) {
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes(@RequestParam(required = false) String statut) {
        return ResponseEntity.ok(commandeService.getAllCommandes(statut));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandesByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(commandeService.getCommandesByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Integer id, @Valid @RequestBody Commande commande) {
        return ResponseEntity.ok(commandeService.updateCommande(id, commande));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Integer id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}