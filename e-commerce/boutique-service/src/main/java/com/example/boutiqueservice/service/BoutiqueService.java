package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Boutique;
import com.example.boutiqueservice.repository.BoutiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoutiqueService {
    @Autowired
    private BoutiqueRepository boutiqueRepository;

    public Boutique createBoutique(Boutique boutique) {
        return boutiqueRepository.save(boutique);
    }

    public Optional<Boutique> getBoutiqueById(Integer id) {
        return boutiqueRepository.findById(id);
    }

    public List<Boutique> getBoutiquesByVendeur(String idVendeur) {
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
}