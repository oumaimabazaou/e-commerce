// Chemin: commande-service/src/main/java/com/example/commandeservice/repository/LigneCommandeRepository.java
package com.example.commandeservice.repository;

import com.example.commandeservice.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Integer> {

    // CORRECTION : Le nom de la méthode doit correspondre au champ `id` de l'entité `Commande`.
    List<LigneCommande> findByCommandeId(Integer commandeId);

}