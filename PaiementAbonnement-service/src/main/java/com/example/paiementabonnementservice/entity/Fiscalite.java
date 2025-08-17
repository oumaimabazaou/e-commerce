package com.example.paiementabonnementservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
}