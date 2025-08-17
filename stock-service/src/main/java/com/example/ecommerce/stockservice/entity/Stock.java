package com.example.ecommerce.stockservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Integer idStock;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "id_boutique")
    private Integer idBoutique;

    @Column(name = "quantite_disponible")
    private Integer quantiteDisponible;

    @Column(name = "seuil_critique")
    private Integer seuilCritique;

    @Column(name = "capacite_maximale")
    private Integer capaciteMaximale;

    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "derniere_maj")
    private LocalDateTime derniereMaj;

    @Column(name = "statut")
    private String statut;

    @Column(name = "location")
    private String location;

    public Stock() {
    }

    public Stock(Integer idProduit, Integer idBoutique, Integer quantiteDisponible,
                 Integer seuilCritique, Integer capaciteMaximale, BigDecimal prixUnitaire, String statut, String location) {
        this.idProduit = idProduit;
        this.idBoutique = idBoutique;
        this.quantiteDisponible = quantiteDisponible;
        this.seuilCritique = seuilCritique;
        this.capaciteMaximale = capaciteMaximale;
        this.prixUnitaire = prixUnitaire;
        this.statut = statut;
        this.location = location;
        this.derniereMaj = LocalDateTime.now();
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

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

    public Integer getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(Integer quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public Integer getSeuilCritique() {
        return seuilCritique;
    }

    public void setSeuilCritique(Integer seuilCritique) {
        this.seuilCritique = seuilCritique;
    }

    public Integer getCapaciteMaximale() {
        return capaciteMaximale;
    }

    public void setCapaciteMaximale(Integer capaciteMaximale) {
        this.capaciteMaximale = capaciteMaximale;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDateTime getDerniereMaj() {
        return derniereMaj;
    }

    public void setDerniereMaj(LocalDateTime derniereMaj) {
        this.derniereMaj = derniereMaj;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "idStock=" + idStock +
                ", idProduit=" + idProduit +
                ", idBoutique=" + idBoutique +
                ", quantiteDisponible=" + quantiteDisponible +
                ", seuilCritique=" + seuilCritique +
                ", capaciteMaximale=" + capaciteMaximale +
                ", prixUnitaire=" + prixUnitaire +
                ", derniereMaj=" + derniereMaj +
                ", statut='" + statut + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}