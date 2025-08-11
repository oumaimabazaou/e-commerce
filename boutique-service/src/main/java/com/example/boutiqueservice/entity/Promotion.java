package com.example.boutiqueservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromotion;

    @Column(name = "id_boutique", nullable = false)
    private Integer idBoutique;

    @Column(nullable = false)
    private String nom;

    private String description;

    private Double pourcentage;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(columnDefinition = "VARCHAR(255)")
    private String produitsEligibles;

    @Column(columnDefinition = "VARCHAR(255)")
    private String conditionsUtilisation;

    @Column(name = "utilisations_max")
    private Integer utilisationsMax;

    @Column(name = "utilisations_actuelles")
    private Integer utilisationsActuelles;

    private Boolean active;

    // Getters and Setters
    public Integer getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(Integer idPromotion) {
        this.idPromotion = idPromotion;
    }

    public Integer getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Double pourcentage) {
        this.pourcentage = pourcentage;
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

    public String getProduitsEligibles() {
        return produitsEligibles;
    }

    public void setProduitsEligibles(String produitsEligibles) {
        this.produitsEligibles = produitsEligibles;
    }

    public String getConditionsUtilisation() {
        return conditionsUtilisation;
    }

    public void setConditionsUtilisation(String conditionsUtilisation) {
        this.conditionsUtilisation = conditionsUtilisation;
    }

    public Integer getUtilisationsMax() {
        return utilisationsMax;
    }

    public void setUtilisationsMax(Integer utilisationsMax) {
        this.utilisationsMax = utilisationsMax;
    }

    public Integer getUtilisationsActuelles() {
        return utilisationsActuelles;
    }

    public void setUtilisationsActuelles(Integer utilisationsActuelles) {
        this.utilisationsActuelles = utilisationsActuelles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}