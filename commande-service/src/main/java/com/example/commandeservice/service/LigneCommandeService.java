package com.example.commandeservice.service;

import com.example.commandeservice.entity.LigneCommande;
import java.util.List;
import java.util.Optional;

public interface LigneCommandeService {
    LigneCommande createLigneCommande(LigneCommande ligneCommande);
    Optional<LigneCommande> getLigneCommandeById(Integer id);
    List<LigneCommande> getLigneCommandesByCommande(Integer commandeId);
    LigneCommande updateLigneCommande(Integer id, LigneCommande ligneCommande);
    void deleteLigneCommande(Integer id);
}