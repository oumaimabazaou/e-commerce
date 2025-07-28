package com.example.boutiqueservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "boutique")
public class Boutique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoutique;

    private String nomBoutique;
    private String description;
    private String adresse;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Lob
    private String horairesOuverture; // JSON
    private String contact;
    private String photoBoutique;
    private String autorisationImage;
    private String ville;
    private Integer codePostal;
    private String pays;
    private Integer rayonLivraison;
    private LocalDateTime dateCreation;
    private Boolean active;

    @Column(name = "id_vendeur")
    private String idVendeur; // FK

    // Getters and Setters
    public Integer getIdBoutique() { return idBoutique; }
    public void setIdBoutique(Integer idBoutique) { this.idBoutique = idBoutique; }
    public String getNomBoutique() { return nomBoutique; }
    public void setNomBoutique(String nomBoutique) { this.nomBoutique = nomBoutique; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public String getHorairesOuverture() { return horairesOuverture; }
    public void setHorairesOuverture(String horairesOuverture) { this.horairesOuverture = horairesOuverture; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getPhotoBoutique() { return photoBoutique; }
    public void setPhotoBoutique(String photoBoutique) { this.photoBoutique = photoBoutique; }
    public String getAutorisationImage() { return autorisationImage; }
    public void setAutorisationImage(String autorisationImage) { this.autorisationImage = autorisationImage; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public Integer getCodePostal() { return codePostal; }
    public void setCodePostal(Integer codePostal) { this.codePostal = codePostal; }
    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
    public Integer getRayonLivraison() { return rayonLivraison; }
    public void setRayonLivraison(Integer rayonLivraison) { this.rayonLivraison = rayonLivraison; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public String getIdVendeur() { return idVendeur; }
    public void setIdVendeur(String idVendeur) { this.idVendeur = idVendeur; }
}