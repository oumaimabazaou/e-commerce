package com.example.commandeservice.service;

import com.example.commandeservice.entity.Commande;
import com.example.commandeservice.entity.LigneCommande;
import com.example.commandeservice.repository.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    private static final Logger logger = LoggerFactory.getLogger(CommandeServiceImpl.class);

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    @Transactional
    public Commande createCommande(Commande commande) {
        logger.info("Tentative de création de la commande : {}", commande);
        try {
            // Vérification des champs obligatoires
            if (commande.getIdClient() == null || commande.getIdClient().trim().isEmpty()) {
                throw new IllegalArgumentException("idClient est obligatoire");
            }
            if (commande.getStatut() == null || commande.getStatut().trim().isEmpty()) {
                throw new IllegalArgumentException("statut est obligatoire");
            }

            // Initialiser les timestamps
            commande.setCreatedAt(LocalDateTime.now());
            commande.setUpdatedAt(LocalDateTime.now());

            // Gérer ligneCommandes
            if (commande.getLigneCommandes() == null) {
                commande.setLigneCommandes(Collections.emptyList());
            } else {
                for (LigneCommande ligne : commande.getLigneCommandes()) {
                    if (ligne.getIdProduit() == null) {
                        throw new IllegalArgumentException("idProduit est obligatoire pour chaque ligne");
                    }
                    if (ligne.getQuantite() == null || ligne.getQuantite() <= 0) {
                        throw new IllegalArgumentException("quantite doit être positive");
                    }
                    if (ligne.getPrixUnitaire() == null || ligne.getPrixUnitaire() <= 0) {
                        throw new IllegalArgumentException("prixUnitaire doit être positif");
                    }
                    if (ligne.getCommande() == null) {
                        ligne.setCommande(commande);
                    }
                    // Calculer sousTotal pour chaque ligne
                    ligne.setSousTotal(ligne.getQuantite() * ligne.getPrixUnitaire());
                }
            }

            // Calculer le total
            double total = calculateTotal(commande);
            commande.setTotal(total);

            Commande savedCommande = commandeRepository.save(commande);
            logger.info("Commande créée avec succès : {}", savedCommande);
            return savedCommande;
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la commande : ", e);
            throw e;
        }
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
        existingCommande.setStatut(commande.getStatut() != null ? commande.getStatut() : existingCommande.getStatut());
        existingCommande.setUpdatedAt(LocalDateTime.now());
        if (commande.getLigneCommandes() != null) {
            existingCommande.setLigneCommandes(commande.getLigneCommandes());
            existingCommande.getLigneCommandes().forEach(ligne -> {
                if (ligne.getCommande() == null) ligne.setCommande(existingCommande);
            });
        }
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