package com.example.communicationservice.controller;

import com.example.communicationservice.entity.Notification;
import com.example.communicationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Notification>> getNotificationsByUtilisateur(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(notificationService.getNotificationsByUtilisateur(idUtilisateur));
    }

    @GetMapping("/unread/{idUtilisateur}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(idUtilisateur));
    }
}