package com.example.communicationservice.service;

import com.example.communicationservice.entity.Notification;
import com.example.communicationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public Notification createNotification(Notification notification) {
        // ✅ VALIDATION ET VALEURS PAR DÉFAUT
        if (notification.getDateNotification() == null) {
            notification.setDateNotification(LocalDateTime.now());
        }
        if (notification.getLu() == null) {
            notification.setLu(false);
        }

        // ✅ LOG POUR DEBUG
        System.out.println("📝 Création notification: " + notification.getMessage());
        System.out.println("👤 Pour utilisateur ID: " + notification.getIdUtilisateur());

        Notification saved = notificationRepository.save(notification);

        // ✅ LOG CONFIRMATION
        System.out.println("✅ Notification sauvée avec ID: " + saved.getIdNotification());

        return saved;
    }

    public Optional<Notification> getNotificationById(Integer id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsByUtilisateur(Integer idUtilisateur) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getIdUtilisateur().equals(idUtilisateur))
                .sorted((a, b) -> b.getDateNotification().compareTo(a.getDateNotification()))
                .toList();
    }

    public List<Notification> getUnreadNotifications(Integer idUtilisateur) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getIdUtilisateur().equals(idUtilisateur) && !n.getLu())
                .sorted((a, b) -> b.getDateNotification().compareTo(a.getDateNotification()))
                .toList();
    }

    @Transactional
    public Optional<Notification> markAsRead(Integer id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            Notification notif = notification.get();
            notif.setLu(true);
            return Optional.of(notificationRepository.save(notif));
        }
        return Optional.empty();
    }

    @Transactional
    public void markAllAsRead(Integer idUtilisateur) {
        List<Notification> notifications = getNotificationsByUtilisateur(idUtilisateur);
        notifications.forEach(notification -> {
            notification.setLu(true);
            notificationRepository.save(notification);
        });
    }

    @Transactional
    public boolean deleteNotification(Integer id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public int getUnreadCount(Integer idUtilisateur) {
        return (int) notificationRepository.findAll().stream()
                .filter(n -> n.getIdUtilisateur().equals(idUtilisateur) && !n.getLu())
                .count();
    }
}