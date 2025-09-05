package com.example.paiementabonnementservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fiscalite")
public class Fiscalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFiscalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paiement", nullable = false)
    private Paiement paiement; // Remplace idPaiement par une relation

    @Column(name = "taux_taxe")
    private Double tauxTaxe;

    @Column(name = "montant_taxe")
    private Double montantTaxe;

    @Column(name = "type_taxe")
    private String typeTaxe;

    // Getters and Setters
    public Integer getIdFiscalite() {
        return idFiscalite;
    }

    public void setIdFiscalite(Integer idFiscalite) {
        this.idFiscalite = idFiscalite;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Double getTauxTaxe() {
        return tauxTaxe;
    }

    public void setTauxTaxe(Double tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
    }

    public Double getMontantTaxe() {
        return montantTaxe;
    }

    public void setMontantTaxe(Double montantTaxe) {
        this.montantTaxe = montantTaxe;
    }

    public String getTypeTaxe() {
        return typeTaxe;
    }

    public void setTypeTaxe(String typeTaxe) {
        this.typeTaxe = typeTaxe;
    }
}