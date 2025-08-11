package com.example.evaluationservice.controller;

import com.example.evaluationservice.entity.Favoris;
import com.example.evaluationservice.service.FavorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoris")
public class FavorisController {
    @Autowired
    private FavorisService favorisService;

    @GetMapping
    public ResponseEntity<List<Favoris>> getAllFavoris() {
        return ResponseEntity.ok(favorisService.getAllFavoris());
    }

    @PostMapping
    public ResponseEntity<Favoris> createFavori(@RequestBody Favoris favoris) {
        return ResponseEntity.ok(favorisService.createFavori(favoris));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favoris> getFavoriById(@PathVariable Integer id) {
        return favorisService.getFavoriById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Favoris>> getFavorisByUtilisateur(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(favorisService.getFavorisByUtilisateur(idUtilisateur));
    }

    @GetMapping("/entite/{idEntiteFavoree}")
    public ResponseEntity<List<Favoris>> getFavorisByEntite(@PathVariable Integer idEntiteFavoree) {
        return ResponseEntity.ok(favorisService.getFavorisByEntite(idEntiteFavoree));
    }
}