package com.example.paiementabonnementservice.controller;

import com.example.paiementabonnementservice.entity.Paiement;
import com.example.paiementabonnementservice.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {
    @Autowired
    private PaiementService paiementService;

    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        return ResponseEntity.ok(paiementService.createPaiement(paiement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Integer id) {
        return paiementService.getPaiementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/abonnement/{idAbonnement}")
    public ResponseEntity<List<Paiement>> getPaiementsByAbonnement(@PathVariable Integer idAbonnement) {
        return ResponseEntity.ok(paiementService.getPaiementsByAbonnement(idAbonnement));
    }
}