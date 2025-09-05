// Chemin: commande-service/src/main/java/com/example/commandeservice/service/CommandeService.java

package com.example.commandeservice.service;

import com.example.commandeservice.entity.Commande;
import java.util.List;
import java.util.Optional;

public interface CommandeService {
    Commande createCommande(Commande commande);
    Optional<Commande> getCommandeById(Integer id);
    List<Commande> getAllCommandes(String statut);
    Commande updateCommande(Integer id, Commande commande);
    void deleteCommande(Integer id);
    void markPayment(String idCommande);
    List<Commande> getCommandesByClient(String clientId);

    // CORRECTION : AJOUT DE LA MÉTHODE MANQUANTE
    List<Commande> getCommandesByBoutique(Integer boutiqueId);
}