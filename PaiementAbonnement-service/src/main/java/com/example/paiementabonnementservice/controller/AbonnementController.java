package com.example.paiementabonnementservice.controller;

import com.example.paiementabonnementservice.entity.Abonnement;
import com.example.paiementabonnementservice.service.AbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {
    @Autowired
    private AbonnementService abonnementService;

    @PostMapping
    public ResponseEntity<Abonnement> createAbonnement(@RequestBody Abonnement abonnement) {
        return ResponseEntity.ok(abonnementService.createAbonnement(abonnement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Abonnement> getAbonnementById(@PathVariable Integer id) {
        return abonnementService.getAbonnementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Abonnement>> getAbonnementsByUtilisateur(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(abonnementService.getAbonnementsByUtilisateur(idUtilisateur));
    }
}