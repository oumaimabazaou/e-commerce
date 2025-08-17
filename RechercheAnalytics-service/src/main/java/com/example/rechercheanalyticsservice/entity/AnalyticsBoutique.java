package com.example.rechercheanalyticsservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "analytics_boutique")
public class AnalyticsBoutique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnalytics;

    @Column(name = "id_boutique", nullable = false)
    private Integer idBoutique;

    @Column(name = "nombre_visites", nullable = false)
    private Integer nombreVisites;

    @Column(name = "chiffre_affaires", nullable = false)
    private Double chiffreAffaires;

    @Column(name = "date_analyse", nullable = false)
    private LocalDateTime dateAnalyse;

    @Column(name = "periode")
    private String periode; // Ex. "Mensuelle", "Hebdomadaire"
}