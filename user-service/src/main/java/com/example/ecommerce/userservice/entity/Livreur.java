package com.example.ecommerce.userservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "livreur")
public class Livreur {
    @Id
    @Column(name = "id_livreur")
    private String idLivreur;

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    private String typeTransport;
    private BigDecimal capaciteMax;
    @Lob
    private String zonesCouverture; // JSON
    private Boolean disponible;
    private BigDecimal noteMoyenne;
    private Integer nombreLivraisons;
    private String permisConduire;
    // Getters, setters, constructeurs

    public String getIdLivreur() { return idLivreur; }
    public void setIdLivreur(String idLivreur) { this.idLivreur = idLivreur; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public String getTypeTransport() { return typeTransport; }
    public void setTypeTransport(String typeTransport) { this.typeTransport = typeTransport; }
    public BigDecimal getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(BigDecimal capaciteMax) { this.capaciteMax = capaciteMax; }
    public String getZonesCouverture() { return zonesCouverture; }
    public void setZonesCouverture(String zonesCouverture) { this.zonesCouverture = zonesCouverture; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    public BigDecimal getNoteMoyenne() { return noteMoyenne; }
    public void setNoteMoyenne(BigDecimal noteMoyenne) { this.noteMoyenne = noteMoyenne; }
    public Integer getNombreLivraisons() { return nombreLivraisons; }
    public void setNombreLivraisons(Integer nombreLivraisons) { this.nombreLivraisons = nombreLivraisons; }
    public String getPermisConduire() { return permisConduire; }
    public void setPermisConduire(String permisConduire) { this.permisConduire = permisConduire; }
} 