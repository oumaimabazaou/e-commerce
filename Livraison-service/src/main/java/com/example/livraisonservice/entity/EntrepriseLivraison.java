package com.example.livraisonservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "entreprise_livraison")
public class EntrepriseLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntreprise;

    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Column(name = "pays")
    private String pays;

    @Column(name = "type_entreprise")
    private String typeEntreprise;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_telephone")
    private String contactTelephone;

    @Column(name = "zones_couverture", columnDefinition = "TEXT", nullable = true)
    private String zonesCouverture; // Stocke un JSON sous forme de chaîne comme TEXT

    @Column(name = "hub_coordinateur")
    private Boolean hubCoordinateur;

    @Column(name = "capacite_max")
    private Integer capaciteMax;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "entrepriseLivraison")
    private List<Livraison> livraisons;

    // Getters and Setters
    public Integer getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(Integer idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getTypeEntreprise() {
        return typeEntreprise;
    }

    public void setTypeEntreprise(String typeEntreprise) {
        this.typeEntreprise = typeEntreprise;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getZonesCouverture() {
        return zonesCouverture;
    }

    public void setZonesCouverture(String zonesCouverture) {
        this.zonesCouverture = zonesCouverture;
    }

    public Boolean getHubCoordinateur() {
        return hubCoordinateur;
    }

    public void setHubCoordinateur(Boolean hubCoordinateur) {
        this.hubCoordinateur = hubCoordinateur;
    }

    public Integer getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(Integer capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }
}