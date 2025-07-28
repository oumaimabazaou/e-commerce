package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Vendeur;
import com.example.ecommerce.userservice.service.VendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendeurs")
public class VendeurController {
    @Autowired
    private VendeurService vendeurService;

    @GetMapping
    public List<Vendeur> getAll() {
        return vendeurService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Vendeur> getById(@PathVariable String id) {
        return vendeurService.findById(id);
    }

    @PostMapping
    public Vendeur create(@RequestBody Vendeur vendeur) {
        return vendeurService.save(vendeur);
    }

    @PutMapping("/{id}")
    public Vendeur update(@PathVariable String id, @RequestBody Vendeur vendeur) {
        vendeur.setIdVendeur(id);
        return vendeurService.save(vendeur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        vendeurService.deleteById(id);
    }
} 