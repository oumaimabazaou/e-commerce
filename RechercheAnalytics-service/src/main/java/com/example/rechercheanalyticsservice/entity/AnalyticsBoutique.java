package com.example.rechercheanalyticsservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    // Getters and Setters
    public Integer getIdAnalytics() {
        return idAnalytics;
    }

    public void setIdAnalytics(Integer idAnalytics) {
        this.idAnalytics = idAnalytics;
    }

    public Integer getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public Integer getNombreVisites() {
        return nombreVisites;
    }

    public void setNombreVisites(Integer nombreVisites) {
        this.nombreVisites = nombreVisites;
    }

    public Double getChiffreAffaires() {
        return chiffreAffaires;
    }

    public void setChiffreAffaires(Double chiffreAffaires) {
        this.chiffreAffaires = chiffreAffaires;
    }

    public LocalDateTime getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(LocalDateTime dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}