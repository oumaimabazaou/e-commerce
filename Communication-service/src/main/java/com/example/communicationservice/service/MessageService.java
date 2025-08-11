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

    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesByExpediteur(Integer idExpediteur) {
        return messageRepository.findAll().stream()
                .filter(m -> m.getIdExpediteur().equals(idExpediteur))
                .toList();
    }

    public List<Message> getMessagesByDestinataire(Integer idDestinataire) {
        return messageRepository.findAll().stream()
                .filter(m -> m.getIdDestinataire().equals(idDestinataire))
                .toList();
    }
}