package com.example.paiementabonnementservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_abonnement", nullable = false)
    private Integer idAbonnement;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "methode_paiement")
    private String methodePaiement;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "statut_paiement")
    private String statutPaiement;
}