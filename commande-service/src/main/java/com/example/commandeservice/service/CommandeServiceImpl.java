// Chemin: commande-service/src/main/java/com/example/commandeservice/service/CommandeServiceImpl.java

package com.example.commandeservice.service;

import com.example.commandeservice.entity.Commande;
import com.example.commandeservice.entity.LigneCommande;
import com.example.commandeservice.repository.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// On doit définir la classe Notification pour qu'elle soit utilisable
class Notification {
    private String type;
    private Object data;

    public Notification(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    // Getters nécessaires pour la sérialisation JSON
    public String getType() { return type; }
    public Object getData() { return data; }
}


@Service
public class CommandeServiceImpl implements CommandeService {

    private static final Logger logger = LoggerFactory.getLogger(CommandeServiceImpl.class);

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public Commande createCommande(Commande commande) {
        logger.info("Tentative de création de la commande : {}", commande);
        try {
            if (commande.getIdClient() == null || commande.getIdClient().trim().isEmpty()) {
                throw new IllegalArgumentException("idClient est obligatoire");
            }
            if (commande.getStatut() == null || commande.getStatut().trim().isEmpty()) {
                throw new IllegalArgumentException("statut est obligatoire");
            }

            commande.setCreatedAt(LocalDateTime.now());
            commande.setUpdatedAt(LocalDateTime.now());

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
                    ligne.setCommande(commande); // Assurer la liaison bidirectionnelle
                    ligne.setSousTotal(ligne.getQuantite() * ligne.getPrixUnitaire());
                }
            }

            double total = calculateTotal(commande);
            commande.setMontantTotal(total); // S'assurer que le bon champ est mis à jour

            Commande savedCommande = commandeRepository.save(commande);
            logger.info("Commande créée avec succès : {}", savedCommande);

            notifyVendor(savedCommande.getIdBoutique(), "new_order", savedCommande);

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
        if (statut != null && !statut.isEmpty()) {
            return commandeRepository.findByStatut(statut);
        }
        return commandeRepository.findAll();
    }

    @Override
    @Transactional
    public Commande updateCommande(Integer id, Commande commandeDetails) {
        Commande existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));

        if (commandeDetails.getStatut() != null) {
            existingCommande.setStatut(commandeDetails.getStatut());
        }
        existingCommande.setUpdatedAt(LocalDateTime.now());

        // ... (ajouter d'autres logiques de mise à jour si nécessaire)

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

    // CORRECTION : IMPLÉMENTATION DE LA MÉTHODE MANQUANTE
    @Override
    public List<Commande> getCommandesByBoutique(Integer boutiqueId) {
        return commandeRepository.findByIdBoutique(boutiqueId);
    }

    @Override
    @Transactional
    public void markPayment(String idCommande) {
        Commande commande = commandeRepository.findById(Integer.parseInt(idCommande))
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + idCommande));
        commande.setStatut("Payée");
        commande.setUpdatedAt(LocalDateTime.now());
        commandeRepository.save(commande);
        notifyVendor(commande.getIdBoutique(), "payment", idCommande);
    }

    private double calculateTotal(Commande commande) {
        double ligneTotal = 0.0;
        if (commande.getLigneCommandes() != null) {
            ligneTotal = commande.getLigneCommandes().stream()
                    .mapToDouble(ligne -> ligne.getSousTotal() != null ? ligne.getSousTotal() : 0.0)
                    .sum();
        }
        double shippingFee = commande.getFraisLivraison() != null ? commande.getFraisLivraison() : 0.0;
        return ligneTotal + shippingFee;
    }

    private void notifyVendor(Integer boutiqueId, String type, Object data) {
        // Cette logique doit être améliorée pour trouver le vrai ID du vendeur
        // Par exemple, en faisant un appel à un 'boutique-service'
        String vendeurId = getVendeurIdFromBoutique(boutiqueId);
        if (vendeurId != null) {
            messagingTemplate.convertAndSend("/topic/vendor/" + vendeurId, new Notification(type, data));
        } else {
            logger.warn("Aucun vendeur trouvé pour la boutique ID: {}", boutiqueId);
        }
    }

    private String getVendeurIdFromBoutique(Integer boutiqueId) {

        return "vendeur1";
    }
}