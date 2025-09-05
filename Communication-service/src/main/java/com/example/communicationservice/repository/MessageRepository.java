// Chemin: communication-service/src/main/java/com/example/communicationservice/repository/MessageRepository.java

package com.example.communicationservice.repository;

import com.example.communicationservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    // CORRECTION : Ajout de ces méthodes pour des requêtes plus performantes
    List<Message> findByIdExpediteur(Integer idExpediteur);
    List<Message> findByIdDestinataire(Integer idDestinataire);

}