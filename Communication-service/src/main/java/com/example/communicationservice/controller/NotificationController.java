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

    // ✅ CRÉER UNE NOTIFICATION (UTILISÉ PAR LE CLIENT)
    @PostMapping
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) {
        Notification created = notificationService.createNotification(notification);
        return ResponseEntity.ok(created);
    }

    // ✅ RÉCUPÉRER UNE NOTIFICATION PAR ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ RÉCUPÉRER TOUTES LES NOTIFICATIONS D'UN UTILISATEUR
    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Notification>> getNotificationsByUtilisateur(@PathVariable Integer idUtilisateur) {
        List<Notification> notifications = notificationService.getNotificationsByUtilisateur(idUtilisateur);
        return ResponseEntity.ok(notifications);
    }

    // ✅ RÉCUPÉRER LES NOTIFICATIONS NON LUES
    @GetMapping("/unread/{idUtilisateur}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Integer idUtilisateur) {
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications(idUtilisateur);
        return ResponseEntity.ok(unreadNotifications);
    }

    // ✅ MARQUER UNE NOTIFICATION COMME LUE
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Integer id) {
        return notificationService.markAsRead(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ MARQUER TOUTES LES NOTIFICATIONS D'UN UTILISATEUR COMME LUES
    @PutMapping("/utilisateur/{idUtilisateur}/read-all")
    public ResponseEntity<String> markAllAsRead(@PathVariable Integer idUtilisateur) {
        notificationService.markAllAsRead(idUtilisateur);
        return ResponseEntity.ok("Toutes les notifications marquées comme lues");
    }

    // ✅ SUPPRIMER UNE NOTIFICATION
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Integer id) {
        boolean deleted = notificationService.deleteNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Notification supprimée");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ COMPTER LES NOTIFICATIONS NON LUES
    @GetMapping("/utilisateur/{idUtilisateur}/unread-count")
    public ResponseEntity<Integer> getUnreadCount(@PathVariable Integer idUtilisateur) {
        int count = notificationService.getUnreadCount(idUtilisateur);
        return ResponseEntity.ok(count);
    }
}