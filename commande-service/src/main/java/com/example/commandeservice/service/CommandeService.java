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
    List<Commande> getCommandesByClient(String clientId);
}