// CHEMIN : commande-service/src/main/java/com/example/commandeservice/repository/LitigeRepository.java
package com.example.commandeservice.repository;

import com.example.commandeservice.entity.Litige;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LitigeRepository extends JpaRepository<Litige, Integer> {

    // CORRECTION DÉFINITIVE : Le nom de la méthode doit être basé sur les champs Java.
    // Pour trouver par l'ID de l'objet 'commande', la méthode est :
    List<Litige> findByCommandeId(Integer commandeId);

}