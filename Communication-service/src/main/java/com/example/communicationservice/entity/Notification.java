package com.example.communicationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotification;

    @Column(nullable = false)
    @NotNull(message = "idUtilisateur est requis")
    private Integer idUtilisateur;

    @Column(nullable = false)
    @NotNull(message = "typeNotification est requis")
    private String typeNotification;

    @Column(nullable = false)
    @NotNull(message = "message est requis")
    private String message;

    private LocalDateTime dateNotification;
    private Boolean lu;
}