package com.example.livraisonservice.service;

import com.example.livraisonservice.entity.EntrepriseLivraison;
import com.example.livraisonservice.repository.EntrepriseLivraisonRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseLivraisonService {
    @Autowired
    private EntrepriseLivraisonRepository entrepriseLivraisonRepository;

    @Transactional
    public EntrepriseLivraison createEntrepriseLivraison(EntrepriseLivraison entreprise) {
        if (entreprise == null) {
            throw new IllegalArgumentException("L'entité EntrepriseLivraison ne peut pas être null");
        }
        if (entreprise.getNomEntreprise() == null || entreprise.getNomEntreprise().trim().isEmpty()) {
            throw new IllegalArgumentException("nomEntreprise est requis");
        }
        if (entreprise.getContactEmail() != null && !entreprise.getContactEmail().trim().isEmpty() && !isValidEmail(entreprise.getContactEmail())) {
            throw new IllegalArgumentException("contactEmail doit être une adresse email valide");
        }
        if (entreprise.getZonesCouverture() != null && !entreprise.getZonesCouverture().trim().isEmpty()) {
            try {
                new JSONObject(entreprise.getZonesCouverture());
            } catch (JSONException e) {
                throw new IllegalArgumentException("zonesCouverture doit être un JSON valide", e);
            }
        }
        if (entreprise.getCreatedAt() == null) {
            entreprise.setCreatedAt(LocalDateTime.now());
        }
        return entrepriseLivraisonRepository.save(entreprise);
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public Optional<EntrepriseLivraison> getEntrepriseLivraisonById(Integer id) {
        return entrepriseLivraisonRepository.findById(id);
    }

    public List<EntrepriseLivraison> getAllEntreprisesLivraison() {
        return entrepriseLivraisonRepository.findAll();
    }

    @Transactional
    public EntrepriseLivraison updateEntrepriseLivraison(Integer id, EntrepriseLivraison entreprise) {
        return entrepriseLivraisonRepository.findById(id)
                .map(existingEntreprise -> {
                    existingEntreprise.setNomEntreprise(entreprise.getNomEntreprise() != null ? entreprise.getNomEntreprise() : existingEntreprise.getNomEntreprise());
                    existingEntreprise.setPays(entreprise.getPays());
                    existingEntreprise.setTypeEntreprise(entreprise.getTypeEntreprise());
                    existingEntreprise.setContactEmail(entreprise.getContactEmail());
                    existingEntreprise.setContactTelephone(entreprise.getContactTelephone());
                    existingEntreprise.setZonesCouverture(entreprise.getZonesCouverture());
                    existingEntreprise.setHubCoordinateur(entreprise.getHubCoordinateur());
                    existingEntreprise.setCapaciteMax(entreprise.getCapaciteMax());
                    existingEntreprise.setCreatedAt(entreprise.getCreatedAt() != null ? entreprise.getCreatedAt() : existingEntreprise.getCreatedAt());
                    return entrepriseLivraisonRepository.save(existingEntreprise);
                }).orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));
    }

    @Transactional
    public void deleteEntrepriseLivraison(Integer id) {
        entrepriseLivraisonRepository.deleteById(id);
    }
}