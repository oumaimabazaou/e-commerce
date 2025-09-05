package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Utilisateur;
import com.example.ecommerce.userservice.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.findAll(); // Corrélé à findAll dans UtilisateurService
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // À améliorer avec un message d'erreur
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        try {
            return utilisateurService.findById(id) // Corrélé à findById dans UtilisateurService
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // À améliorer avec un message d'erreur
        }
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@Valid @RequestBody Utilisateur utilisateur) {
        try {
            if (utilisateur.getEmail() == null || utilisateur.getMotDePasse() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            Utilisateur savedUtilisateur = utilisateurService.save(utilisateur); // Corrélé à save dans UtilisateurService

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUtilisateur.getIdUtilisateur())
                    .toUri();
            return ResponseEntity.created(location).body(savedUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // À améliorer avec un message d'erreur
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @Valid @RequestBody Utilisateur utilisateur) {
        try {
            if (!id.equals(utilisateur.getIdUtilisateur())) {
                return ResponseEntity.badRequest().body(null);
            }

            if (utilisateur.getMotDePasse() != null && !utilisateur.getMotDePasse().isEmpty()) {
                utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            }
            Utilisateur updatedUtilisateur = utilisateurService.save(utilisateur); // Corrélé à save dans UtilisateurService
            return ResponseEntity.ok(updatedUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // À améliorer avec un message d'erreur
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            utilisateurService.deleteById(id); // Corrélé à deleteById dans UtilisateurService
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}