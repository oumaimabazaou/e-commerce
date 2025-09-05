// Chemin: commande-service/src/main/java/com/example/commandeservice/service/LigneCommandeService.java
package com.example.commandeservice.service;

import com.example.commandeservice.entity.LigneCommande;
import java.util.List;
import java.util.Optional;

public interface LigneCommandeService {
    LigneCommande createLigneCommande(LigneCommande ligneCommande);

    // CORRECTION : Changé de Long à Integer
    Optional<LigneCommande> getLigneCommandeById(Integer id);

    // CORRECTION : Changé de Long à Integer
    List<LigneCommande> getLigneCommandesByCommande(Integer commandeId);

    // CORRECTION : Changé de Long à Integer
    LigneCommande updateLigneCommande(Integer id, LigneCommande ligneCommande);

    // CORRECTION : Changé de Long à Integer
    void deleteLigneCommande(Integer id);
}