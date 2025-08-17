package com.example.commandeservice.controller;

import com.example.commandeservice.entity.Litige;
import com.example.commandeservice.service.LitigeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/litiges")
public class LitigeController {
    @Autowired
    private LitigeService litigeService;

    @PostMapping
    public ResponseEntity<Litige> createLitige(@Valid @RequestBody Litige litige) {
        return ResponseEntity.ok(litigeService.createLitige(litige));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Litige>> getLitigeById(@PathVariable Integer id) {
        return ResponseEntity.ok(litigeService.getLitigeById(id));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<Litige>> getLitigesByCommande(@PathVariable Integer commandeId) {
        return ResponseEntity.ok(litigeService.getLitigesByCommande(commandeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Litige> updateLitige(@PathVariable Integer id, @Valid @RequestBody Litige litige) {
        return ResponseEntity.ok(litigeService.updateLitige(id, litige));
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveLitige(@PathVariable Integer id, @RequestBody String resolution) {
        litigeService.resolveLitige(id, resolution);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLitige(@PathVariable Integer id) {
        litigeService.deleteLitige(id);
        return ResponseEntity.noContent().build();
    }
}