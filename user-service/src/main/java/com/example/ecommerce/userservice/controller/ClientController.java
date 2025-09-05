package com.example.ecommerce.userservice.controller;

import com.example.ecommerce.userservice.entity.Client;
import com.example.ecommerce.userservice.entity.Utilisateur;
import com.example.ecommerce.userservice.service.ClientService;
import com.example.ecommerce.userservice.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getById(@PathVariable Long id) { // Changé de String à Long
        return clientService.findById(id);
    }

    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client) { // Changé de String à Long
        client.setIdClient(id);
        return clientService.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { // Changé de String à Long
        clientService.deleteById(id);
    }
    // ✅ Nouvelle API pour récupérer le profil complet
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        Optional<Utilisateur> optionalUser = utilisateurService.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Utilisateur utilisateur = optionalUser.get();

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("nom", utilisateur.getNom());
        profileData.put("prenom", utilisateur.getPrenom());
        profileData.put("email", utilisateur.getEmail());
        profileData.put("telephone", utilisateur.getTelephone());
        profileData.put("adresseComplete", utilisateur.getAdresseComplete());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", profileData);

        return ResponseEntity.ok(response);
    }
}