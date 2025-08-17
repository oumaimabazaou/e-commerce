package com.example.boutiqueservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicite")
public class Publicite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPublicite;

    @Column(nullable = false, length = 100)
    private String titre;

    @Column(length = 500)
    private String description;

    @Column(name = "type_publicite", length = 50)
    private String typePublicite;

    @Column(length = 100)
    private String cible;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "video_url", length = 255)
    private String videoUrl;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    private Boolean active;

    @Column(name = "id_boutique", nullable = false)
    private Integer idBoutique;

    @Column(columnDefinition = "VARCHAR(255)") // Ajusté pour compatibilité
    private String statistiques;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    // Getters and Setters
    public Integer getIdPublicite() {
        return idPublicite;
    }

    public void setIdPublicite(Integer idPublicite) {
        this.idPublicite = idPublicite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypePublicite() {
        return typePublicite;
    }

    public void setTypePublicite(String typePublicite) {
        this.typePublicite = typePublicite;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getStatistiques() {
        return statistiques;
    }

    public void setStatistiques(String statistiques) {
        this.statistiques = statistiques;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}