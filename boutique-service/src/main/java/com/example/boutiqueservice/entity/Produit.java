package com.example.boutiqueservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduit;

    @Column(name = "id_boutique", nullable = false)
    private Integer idBoutique;

    @Column(name = "id_categorie", nullable = false)
    private String idCategorie;

    @Column(name = "nom_produit", nullable = false)
    private String nomProduit;

    private String description;

    private String detail;

    private Double prix;

    @Column(name = "code_barre", unique = true) // Ajout d'une contrainte unique si applicable
    private String codeBarre;

    @Column(columnDefinition = "VARCHAR(255)") // Ajusté pour compatibilité
    private String photos;

    @Column(columnDefinition = "VARCHAR(255)") // Ajusté pour compatibilité
    private String videos;

    @Column(columnDefinition = "VARCHAR(255)") // Ajusté pour compatibilité
    private String dimensions;

    private Double poids;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(name = "date_ajout")
    private LocalDateTime dateAjout;

    private Boolean actif;

    // Getters and Setters
    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Integer getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}