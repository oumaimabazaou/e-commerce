// Chemin: communication-service/src/main/java/com/example/communicationservice/service/MessageService.java

package com.example.communicationservice.service;

import com.example.communicationservice.entity.Message;
import com.example.communicationservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public Message createMessage(Message message) {
        message.setDateEnvoi(LocalDateTime.now());
        message.setStatut("Envoyé");
        return messageRepository.save(message);
    }

    // CORRECTION : Ajout de la méthode pour récupérer tous les messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesByExpediteur(Integer idExpediteur) {
        // C'est mieux de créer une méthode dédiée dans le repository pour la performance
        return messageRepository.findByIdExpediteur(idExpediteur);
    }

    public List<Message> getMessagesByDestinataire(Integer idDestinataire) {
        // C'est mieux de créer une méthode dédiée dans le repository pour la performance
        return messageRepository.findByIdDestinataire(idDestinataire);
    }
}