package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Publicite;
import com.example.boutiqueservice.service.PubliciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicites")
public class PubliciteController {
    @Autowired
    private PubliciteService publiciteService;

    @PostMapping
    public ResponseEntity<Publicite> createPublicite(@RequestBody Publicite publicite) {
        return ResponseEntity.ok(publiciteService.createPublicite(publicite));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicite> getPubliciteById(@PathVariable Integer id) {
        return publiciteService.getPubliciteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Publicite>> getPublicitesByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(publiciteService.getPublicitesByBoutique(idBoutique));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicite> updatePublicite(@PathVariable Integer id, @RequestBody Publicite publiciteDetails) {
        return ResponseEntity.ok(publiciteService.updatePublicite(id, publiciteDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicite(@PathVariable Integer id) {
        publiciteService.deletePublicite(id);
        return ResponseEntity.noContent().build();
    }
}