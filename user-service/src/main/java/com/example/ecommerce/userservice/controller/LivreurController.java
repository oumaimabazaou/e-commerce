package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Livreur;
import com.example.ecommerce.userservice.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {
    @Autowired
    private LivreurService livreurService;

    @GetMapping
    public List<Livreur> getAll() {
        return livreurService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Livreur> getById(@PathVariable Long id) { // Changé de String à Long
        return livreurService.findById(id);
    }

    @PostMapping
    public Livreur create(@RequestBody Livreur livreur) {
        return livreurService.save(livreur);
    }

    @PutMapping("/{id}")
    public Livreur update(@PathVariable Long id, @RequestBody Livreur livreur) { // Changé de String à Long
        livreur.setIdLivreur(id);
        return livreurService.save(livreur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { // Changé de String à Long
        livreurService.deleteById(id);
    }
}