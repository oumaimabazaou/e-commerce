// Chemin: commande-service/src/main/java/com/example/commandeservice/service/LigneCommandeServiceImpl.java
package com.example.commandeservice.service;

import com.example.commandeservice.entity.LigneCommande;
import com.example.commandeservice.repository.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LigneCommandeServiceImpl implements LigneCommandeService {
    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Override
    @Transactional
    public LigneCommande createLigneCommande(LigneCommande ligneCommande) {
        ligneCommande.setSousTotal(ligneCommande.getQuantite() * ligneCommande.getPrixUnitaire());
        return ligneCommandeRepository.save(ligneCommande);
    }

    @Override
    public Optional<LigneCommande> getLigneCommandeById(Integer id) { // <-- CORRECTION
        return ligneCommandeRepository.findById(id);
    }

    @Override
    public List<LigneCommande> getLigneCommandesByCommande(Integer commandeId) { // <-- CORRECTION
        return ligneCommandeRepository.findByCommandeId(commandeId); // Appel de la méthode corrigée
    }

    @Override
    @Transactional
    public LigneCommande updateLigneCommande(Integer id, LigneCommande ligneCommande) { // <-- CORRECTION
        LigneCommande existing = ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée"));
        existing.setQuantite(ligneCommande.getQuantite());
        existing.setPrixUnitaire(ligneCommande.getPrixUnitaire());
        existing.setSousTotal(ligneCommande.getQuantite() * ligneCommande.getPrixUnitaire());
        return ligneCommandeRepository.save(existing);
    }

    @Override
    public void deleteLigneCommande(Integer id) { // <-- CORRECTION
        ligneCommandeRepository.deleteById(id);
    }
}