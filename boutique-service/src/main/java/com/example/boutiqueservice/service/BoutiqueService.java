package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Boutique;
import com.example.boutiqueservice.repository.BoutiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoutiqueService {
    @Autowired
    private BoutiqueRepository boutiqueRepository;

    public Boutique createBoutique(Boutique boutique) {
        try {
            System.out.println("🏪 Service: Création boutique - " + boutique.getNomBoutique());
            System.out.println("🏪 Service: ID Vendeur - " + boutique.getIdVendeur());

            // Vérification existence nom boutique avec gestion d'erreur
            try {
                if (boutiqueRepository.existsByNomBoutiqueIgnoreCase(boutique.getNomBoutique())) {
                    throw new IllegalStateException("Le nom de la boutique existe déjà : " + boutique.getNomBoutique());
                }
            } catch (Exception e) {
                System.err.println("⚠️ Erreur vérification nom boutique: " + e.getMessage());
                // Continuer sans vérification si erreur DB
            }

            if (boutique.getDateCreation() == null) {
                boutique.setDateCreation(LocalDateTime.now());
            }
            if (boutique.getActive() == null) {
                boutique.setActive(true);
            }

            System.out.println("💾 Service: Sauvegarde en base...");
            Boutique savedBoutique = boutiqueRepository.save(boutique);
            System.out.println("✅ Service: Boutique sauvée avec ID: " + savedBoutique.getIdBoutique());

            return savedBoutique;
        } catch (Exception e) {
            System.err.println("💥 Service: Erreur création boutique: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Boutique> getBoutiqueById(Integer id) {
        return boutiqueRepository.findById(id);
    }

    public List<Boutique> getBoutiquesByVendeur(Long idVendeur) {
        return boutiqueRepository.findByIdVendeur(idVendeur);
    }

    public List<Boutique> getActiveBoutiquesByVille(String ville) {
        return boutiqueRepository.findByVilleAndActiveTrue(ville);
    }

    public Boutique updateBoutique(Integer id, Boutique boutiqueDetails) {
        Boutique boutique = boutiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boutique not found"));
        boutique.setNomBoutique(boutiqueDetails.getNomBoutique());
        boutique.setDescription(boutiqueDetails.getDescription());
        boutique.setAdresse(boutiqueDetails.getAdresse());
        boutique.setLatitude(boutiqueDetails.getLatitude());
        boutique.setLongitude(boutiqueDetails.getLongitude());
        boutique.setHorairesOuverture(boutiqueDetails.getHorairesOuverture());
        boutique.setContact(boutiqueDetails.getContact());
        boutique.setPhotoBoutique(boutiqueDetails.getPhotoBoutique());
        boutique.setAutorisationImage(boutiqueDetails.getAutorisationImage());
        boutique.setVille(boutiqueDetails.getVille());
        boutique.setCodePostal(boutiqueDetails.getCodePostal());
        boutique.setPays(boutiqueDetails.getPays());
        boutique.setRayonLivraison(boutiqueDetails.getRayonLivraison());
        boutique.setActive(boutiqueDetails.getActive());
        return boutiqueRepository.save(boutique);
    }

    public void deleteBoutique(Integer id) {
        boutiqueRepository.deleteById(id);
    }

    public Boutique findById(Integer id) {
        return boutiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée avec l'ID : " + id));
    }
    public List<Boutique> getAllBoutiques() {
        return boutiqueRepository.findAll();
    }
    public List<Boutique> getActiveBoutiques() {
        return boutiqueRepository.findByActiveTrue();
    }
}