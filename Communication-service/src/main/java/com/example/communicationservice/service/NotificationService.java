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
        notification.setDateNotification(LocalDateTime.now());
        notification.setLu(false);
        return notificationRepository.save(notification);
    }

    public Optional<Notification> getNotificationById(Integer id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsByUtilisateur(Integer idUtilisateur) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getIdUtilisateur().equals(idUtilisateur))
                .toList();
    }

    public List<Notification> getUnreadNotifications(Integer idUtilisateur) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getIdUtilisateur().equals(idUtilisateur) && !n.getLu())
                .toList();
    }
}