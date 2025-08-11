package com.example.rechercheanalyticsservice.controller;

import com.example.rechercheanalyticsservice.entity.Recherche;
import com.example.rechercheanalyticsservice.service.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recherches")
public class RechercheController {
    @Autowired
    private RechercheService rechercheService;

    @PostMapping
    public ResponseEntity<Recherche> createRecherche(@RequestBody Recherche recherche) {
        return ResponseEntity.ok(rechercheService.createRecherche(recherche));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recherche> getRechercheById(@PathVariable Integer id) {
        return rechercheService.getRechercheById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Recherche>> getRecherchesByUtilisateur(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(rechercheService.getRecherchesByUtilisateur(idUtilisateur));
    }
}