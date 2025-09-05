package com.example.paiementabonnementservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "abonnement")
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAbonnement;

    private String typeAbonnement;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String statutAbonnement;
    private Integer idUtilisateur; // Référence à un utilisateur (à valider avec un autre service si intégré)

    // Getters and Setters
    public Integer getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(Integer idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatutAbonnement() {
        return statutAbonnement;
    }

    public void setStatutAbonnement(String statutAbonnement) {
        this.statutAbonnement = statutAbonnement;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}