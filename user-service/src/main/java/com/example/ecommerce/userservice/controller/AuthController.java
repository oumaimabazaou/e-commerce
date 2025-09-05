
package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.dto.BoutiqueDTO;
import com.example.ecommerce.userservice.dto.LoginRequestDTO;
import com.example.ecommerce.userservice.dto.RegistrationRequestDTO;
import com.example.ecommerce.userservice.dto.RegistrationResponseDTO;
import com.example.ecommerce.userservice.entity.Utilisateur;
import com.example.ecommerce.userservice.entity.Vendeur;
import com.example.ecommerce.userservice.service.UtilisateurService;
import com.example.ecommerce.userservice.service.VendeurService;
import com.example.ecommerce.userservice.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private VendeurService vendeurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RestTemplate restTemplate;

    private static final String BOUTIQUE_SERVICE_URL = "http://boutique-service/api/boutiques";

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "User Service is working!", "timestamp", System.currentTimeMillis()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationRequest) {
        logger.info("Requête d'inscription reçue pour : {}", registrationRequest.getEmail());
        try {
            String email = registrationRequest.getEmail();
            String password = registrationRequest.getPassword();
            String name = registrationRequest.getName();
            String type = registrationRequest.getType();
            String phone = registrationRequest.getPhone();
            String address = registrationRequest.getAddress();

            if (email == null || password == null || name == null || type == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Les champs de base sont obligatoires."));
            }
            if (utilisateurService.findByEmail(email.trim().toLowerCase()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Cet email est déjà utilisé."));
            }

            // Règle de gestion : si c'est un vendeur, le nom de la boutique est obligatoire
            if ("VENDOR".equalsIgnoreCase(type)) {
                String nomBoutique = registrationRequest.getNomBoutique();
                if (nomBoutique == null || nomBoutique.trim().isEmpty()) {
                    logger.warn("Tentative d'inscription d'un vendeur sans nom de boutique pour l'email: {}", email);
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Le nom de la boutique est obligatoire pour un vendeur."));
                }
            }

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(email.trim().toLowerCase());
            utilisateur.setMotDePasse(passwordEncoder.encode(password.trim()));
            utilisateur.setNom(name.trim());
            utilisateur.setTelephone(phone != null ? phone.trim() : "");
            utilisateur.setAdresseComplete(address != null ? address.trim() : "");
            utilisateur.setTypeUtilisateur(type.toUpperCase());

            Utilisateur savedUser = utilisateurService.save(utilisateur);
            logger.info("Utilisateur {} enregistré avec ID : {}", type, savedUser.getIdUtilisateur());

            if ("VENDOR".equalsIgnoreCase(type)) {
                try {
                    // Créer l'entité Vendeur et l'associer à l'utilisateur
                    Vendeur vendeur = new Vendeur();
                    vendeur.setUtilisateur(savedUser);
                    vendeur.setNumeroIce(""); // Valeurs par défaut ou depuis la requête si disponibles
                    vendeur.setNumeroPatente("");
                    vendeur.setStatutFiscal("");
                    vendeur.setChiffreAffaires(0.0);
                    vendeur.setVerifie(false);

                    // Sauvegarder l'entité Vendeur
                    Vendeur savedVendeur = vendeurService.save(vendeur);
                    logger.info("Vendeur enregistré avec ID : {}", savedVendeur.getIdVendeur());

                    // Créer la boutique associée au vendeur
                    BoutiqueDTO boutiqueDTO = new BoutiqueDTO();
                    boutiqueDTO.setIdVendeur(savedUser.getIdUtilisateur());
                    boutiqueDTO.setNomBoutique(registrationRequest.getNomBoutique().trim());
                    boutiqueDTO.setAdresse(savedUser.getAdresseComplete());
                    boutiqueDTO.setVille("Casablanca");
                    boutiqueDTO.setActive(true);

                    HttpEntity<BoutiqueDTO> request = new HttpEntity<>(boutiqueDTO);

                    restTemplate.postForEntity(BOUTIQUE_SERVICE_URL, request, String.class);
                    logger.info("SUCCÈS : Demande de création de boutique envoyée pour le vendeur ID : {}",
                            savedUser.getIdUtilisateur());
                } catch (Exception e) {
                    logger.error("ERREUR CRITIQUE lors de la création de la boutique ou du vendeur pour {}: {}", email,
                            e.getMessage());
                    // On annule la création de l'utilisateur pour garder la base de données propre
                    utilisateurService.deleteById(savedUser.getIdUtilisateur());
                    return ResponseEntity.status(500).body(
                            Map.of("error", "Le service des boutiques est indisponible ou erreur lors de la création du vendeur. L'inscription a été annulée."));
                }
            }

            String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getTypeUtilisateur());

            Map<String, Object> userData = Map.of(
                    "id", savedUser.getIdUtilisateur(),
                    "name", savedUser.getNom(),
                    "email", savedUser.getEmail(),
                    "type", savedUser.getTypeUtilisateur(),
                    "token", token);

            RegistrationResponseDTO responseDTO = new RegistrationResponseDTO("Inscription réussie", userData);
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            logger.error("Erreur inattendue lors de l'inscription pour email {}: {}", registrationRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne inattendue: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        logger.info("Requête de connexion reçue pour email : {}", loginRequest.getEmail());
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email et mot de passe requis"));
            }
            Optional<Utilisateur> optionalUser = utilisateurService.findByEmail(email.trim().toLowerCase());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }
            Utilisateur utilisateur = optionalUser.get();
            if (!passwordEncoder.matches(password.trim(), utilisateur.getMotDePasse())) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }

            String token = jwtTokenProvider.generateToken(utilisateur.getEmail(), utilisateur.getTypeUtilisateur());
            Map<String, Object> userData = Map.of(
                    "id", utilisateur.getIdUtilisateur(),
                    "name", utilisateur.getNom(),
                    "email", utilisateur.getEmail(),
                    "type", utilisateur.getTypeUtilisateur(),
                    "token", token);

            RegistrationResponseDTO responseDTO = new RegistrationResponseDTO("Connexion réussie", userData);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            logger.error("Erreur lors de la connexion : ", e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne : " + e.getMessage()));
        }
    }
}