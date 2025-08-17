package com.example.communicationservice.controller;

import com.example.communicationservice.entity.Message;
import com.example.communicationservice.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) {
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/expediteur/{idExpediteur}")
    public ResponseEntity<List<Message>> getMessagesByExpediteur(@PathVariable Integer idExpediteur) {
        return ResponseEntity.ok(messageService.getMessagesByExpediteur(idExpediteur));
    }

    @GetMapping("/destinataire/{idDestinataire}")
    public ResponseEntity<List<Message>> getMessagesByDestinataire(@PathVariable Integer idDestinataire) {
        return ResponseEntity.ok(messageService.getMessagesByDestinataire(idDestinataire));
    }
}