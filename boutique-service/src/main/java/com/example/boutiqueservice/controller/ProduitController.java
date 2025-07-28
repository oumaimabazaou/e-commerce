package com.example.boutiqueservice.controller;

import com.example.boutiqueservice.entity.Produit;
import com.example.boutiqueservice.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.createProduit(produit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Integer id) {
        return produitService.getProduitById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Produit>> getProduitsByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(produitService.getProduitsByBoutique(idBoutique));
    }

    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(@PathVariable String idCategorie) {
        return ResponseEntity.ok(produitService.getProduitsByCategorie(idCategorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Integer id, @RequestBody Produit produitDetails) {
        return ResponseEntity.ok(produitService.updateProduit(id, produitDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Integer id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}