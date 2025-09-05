package com.example.communicationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
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

    // Getters and Setters
    public Integer getIdMessage() { return idMessage; }
    public void setIdMessage(Integer idMessage) { this.idMessage = idMessage; }

    public Integer getIdExpediteur() { return idExpediteur; }
    public void setIdExpediteur(Integer idExpediteur) { this.idExpediteur = idExpediteur; }

    public Integer getIdDestinataire() { return idDestinataire; }
    public void setIdDestinataire(Integer idDestinataire) { this.idDestinataire = idDestinataire; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(LocalDateTime dateEnvoi) { this.dateEnvoi = dateEnvoi; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}