package com.example.commandeservice.service;

import com.example.commandeservice.entity.Commande;
import com.example.commandeservice.entity.LigneCommande;
import com.example.commandeservice.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    @Transactional
    public Commande createCommande(Commande commande) {
        // Initialiser les timestamps
        commande.setCreatedAt(LocalDateTime.now());
        commande.setUpdatedAt(LocalDateTime.now());

        // Initialiser ligneCommandes si null pour éviter NullPointerException
        if (commande.getLigneCommandes() == null) {
            commande.setLigneCommandes(Collections.emptyList());
        } else {
            // Lier les LigneCommande à la commande
            commande.getLigneCommandes().forEach(ligne -> {
                if (ligne.getCommande() == null) {
                    ligne.setCommande(commande);
                }
            });
        }

        // Calculer le total (avec gestion de null)
        double total = calculateTotal(commande);
        commande.setTotal(total);

        return commandeRepository.save(commande);
    }

    @Override
    public Optional<Commande> getCommandeById(Integer id) {
        return commandeRepository.findById(id);
    }

    @Override
    public List<Commande> getAllCommandes(String statut) {
        return (statut != null && !statut.isEmpty()) ? commandeRepository.findByStatut(statut) : commandeRepository.findAll();
    }

    @Override
    @Transactional
    public Commande updateCommande(Integer id, Commande commande) {
        Commande existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));

        // Mettre à jour les champs
        existingCommande.setStatut(commande.getStatut() != null ? commande.getStatut() : existingCommande.getStatut());
        existingCommande.setUpdatedAt(LocalDateTime.now());

        // Gérer ligneCommandes
        if (commande.getLigneCommandes() != null) {
            existingCommande.setLigneCommandes(commande.getLigneCommandes());
            existingCommande.getLigneCommandes().forEach(ligne -> {
                if (ligne.getCommande() == null) {
                    ligne.setCommande(existingCommande);
                }
            });
        }

        // Recalculer le total
        double total = calculateTotal(existingCommande);
        existingCommande.setTotal(total);

        return commandeRepository.save(existingCommande);
    }

    @Override
    public void deleteCommande(Integer id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public List<Commande> getCommandesByClient(String clientId) {
        return commandeRepository.findByIdClient(clientId);
    }

    // Méthode helper pour calculer le total
    private double calculateTotal(Commande commande) {
        double ligneTotal = (commande.getLigneCommandes() != null)
                ? commande.getLigneCommandes().stream()
                .mapToDouble(ligne -> ligne.getSousTotal() != null ? ligne.getSousTotal() : 0.0)
                .sum()
                : 0.0;
        double shippingFee = commande.getShippingFee() != null ? commande.getShippingFee() : 0.0;
        return ligneTotal + shippingFee;
    }
}