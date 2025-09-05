package com.example.evaluationservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoris")
public class Favoris {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavori;

    private Long idUtilisateur;
    private Integer idEntiteFavoree; // Référence à une entité favorisée (ex. EntrepriseLivraison)
    private LocalDateTime dateAjout;

    // Getters and Setters
    public Integer getIdFavori() {
        return idFavori;
    }

    public void setIdFavori(Integer idFavori) {
        this.idFavori = idFavori;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdEntiteFavoree() {
        return idEntiteFavoree;
    }

    public void setIdEntiteFavoree(Integer idEntiteFavoree) {
        this.idEntiteFavoree = idEntiteFavoree;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }
}