package com.example.rechercheanalyticsservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
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
}