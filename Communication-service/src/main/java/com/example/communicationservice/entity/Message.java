package com.example.communicationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMessage;

    @Column(nullable = false)
    @NotNull(message = "idExpediteur est requis")
    private Integer idExpediteur;

    @Column(nullable = false)
    @NotNull(message = "idDestinataire est requis")
    private Integer idDestinataire;

    @Column(nullable = false)
    @NotNull(message = "contenu est requis")
    private String contenu;

    private LocalDateTime dateEnvoi;
    private String statut;
}