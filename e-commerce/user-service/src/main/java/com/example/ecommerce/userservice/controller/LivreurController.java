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
    public Optional<Livreur> getById(@PathVariable String id) {
        return livreurService.findById(id);
    }

    @PostMapping
    public Livreur create(@RequestBody Livreur livreur) {
        return livreurService.save(livreur);
    }

    @PutMapping("/{id}")
    public Livreur update(@PathVariable String id, @RequestBody Livreur livreur) {
        livreur.setIdLivreur(id);
        return livreurService.save(livreur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        livreurService.deleteById(id);
    }
} 