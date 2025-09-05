package com.example.rechercheanalyticsservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recherche")
public class Recherche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecherche;

    private Integer idUtilisateur;
    private String termeRecherche;
    private LocalDateTime dateRecherche;
    @Column(columnDefinition = "TEXT")
    private String resultats; // Stocke une liste d'IDs ou un JSON de résultats

    // Getters and Setters
    public Integer getIdRecherche() {
        return idRecherche;
    }

    public void setIdRecherche(Integer idRecherche) {
        this.idRecherche = idRecherche;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getTermeRecherche() {
        return termeRecherche;
    }

    public void setTermeRecherche(String termeRecherche) {
        this.termeRecherche = termeRecherche;
    }

    public LocalDateTime getDateRecherche() {
        return dateRecherche;
    }

    public void setDateRecherche(LocalDateTime dateRecherche) {
        this.dateRecherche = dateRecherche;
    }

    public String getResultats() {
        return resultats;
    }

    public void setResultats(String resultats) {
        this.resultats = resultats;
    }
}