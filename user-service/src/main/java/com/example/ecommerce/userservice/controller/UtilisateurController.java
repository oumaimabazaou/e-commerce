package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Utilisateur;
import com.example.ecommerce.userservice.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users") // Changé pour éviter le conflit avec /api/auth
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoints CRUD
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll() {
        try {
            return ResponseEntity.ok(utilisateurService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurService.findById(id);
            return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        try {
            return ResponseEntity.ok(utilisateurService.save(utilisateur));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        try {
            if (utilisateur.getIdUtilisateur() == null || !utilisateur.getIdUtilisateur().equals(id)) {
                utilisateur.setIdUtilisateur(id);
            }
            return ResponseEntity.ok(utilisateurService.save(utilisateur));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            utilisateurService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}