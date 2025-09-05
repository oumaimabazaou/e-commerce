// CHEMIN : commande-service/src/main/java/com/example/commandeservice/entity/Litige.java
package com.example.commandeservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "litige")
public class Litige {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLitige;

    @ManyToOne(fetch = FetchType.LAZY)
    // CORRECTION : Le JoinColumn doit pointer vers la colonne 'id' de la table 'commande'
    @JoinColumn(name = "commande_id", referencedColumnName = "id")
    private Commande commande;

    private String description;
    private String statut;
    private String resolution;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_resolution")
    private LocalDateTime dateResolution;
    
    // Ajout explicite des getters et setters pour résoudre le problème de compilation
    public Integer getIdLitige() {
        return idLitige;
    }

    public void setIdLitige(Integer idLitige) {
        this.idLitige = idLitige;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateResolution() {
        return dateResolution;
    }

    public void setDateResolution(LocalDateTime dateResolution) {
        this.dateResolution = dateResolution;
    }
}