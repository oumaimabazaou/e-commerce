package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Utilisateur;
import com.example.ecommerce.userservice.service.UtilisateurService;
import com.example.ecommerce.userservice.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*") // Correction: utiliser originPatterns au lieu de origins
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registrationData) {
        logger.info("Requête d'inscription reçue pour email : {}", registrationData.get("email"));

        try {
            String email = registrationData.get("email");
            String password = registrationData.get("password");
            String name = registrationData.get("name");
            String type = registrationData.get("type");
            String phone = registrationData.get("phone");
            String address = registrationData.get("address");

            // Validation simple
            if (email == null || password == null || name == null || type == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Champs obligatoires manquants"));
            }

            // Vérifier si l'email existe
            Optional<Utilisateur> existingUser = utilisateurService.findByEmail(email.trim().toLowerCase());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Cet email est déjà utilisé"));
            }

            // Créer l'utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(email.trim().toLowerCase());
            utilisateur.setMotDePasse(passwordEncoder.encode(password.trim()));
            utilisateur.setNom(name.trim());
            utilisateur.setPrenom("");
            utilisateur.setTelephone(phone != null ? phone.trim() : "");
            utilisateur.setAdresseComplete(address != null ? address.trim() : "");
            utilisateur.setTypeUtilisateur(type);
            utilisateur.setDateCreation(LocalDateTime.now());
            utilisateur.setActif(true);

            // Sauvegarder
            Utilisateur savedUser = utilisateurService.save(utilisateur);
            logger.info("Utilisateur enregistré avec ID : {}", savedUser.getIdUtilisateur());

            // Générer token
            String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getTypeUtilisateur());

            // Réponse
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inscription réussie");
            response.put("user", Map.of(
                    "id", savedUser.getIdUtilisateur(),
                    "name", savedUser.getNom(),
                    "email", savedUser.getEmail(),
                    "type", savedUser.getTypeUtilisateur()
            ));
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription : ", e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne : " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        logger.info("Requête de connexion reçue pour email : {}", loginData.get("email"));

        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email et mot de passe requis"));
            }

            Optional<Utilisateur> optionalUser = utilisateurService.findByEmail(email.trim().toLowerCase());

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }

            Utilisateur utilisateur = optionalUser.get();

            if (!passwordEncoder.matches(password.trim(), utilisateur.getMotDePasse())) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }

            String token = jwtTokenProvider.generateToken(utilisateur.getEmail(), utilisateur.getTypeUtilisateur());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("user", Map.of(
                    "id", utilisateur.getIdUtilisateur(),
                    "name", utilisateur.getNom(),
                    "email", utilisateur.getEmail(),
                    "type", utilisateur.getTypeUtilisateur()
            ));
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Erreur lors de la connexion : ", e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne : " + e.getMessage()));
        }
    }
}