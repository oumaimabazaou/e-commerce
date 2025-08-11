package com.example.livraisonservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLivraison;

    @Column(name = "id_commande", nullable = false)
    private Integer idCommande;

    @Column(name = "id_livreur", nullable = false)
    private String idLivreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entreprise_livraison", nullable = false)
    private EntrepriseLivraison entrepriseLivraison;

    @Column(name = "type_livraison")
    private String typeLivraison;

    @Column(name = "prix_livraison")
    private Double prixLivraison;

    @Column(name = "statut_livraison")
    private String statutLivraison;

    @Column(name = "adresse_depart")
    private String adresseDepart;

    @Column(name = "adresse_arrivee")
    private String adresseArrivee;

    @Column(name = "coordonnees_depart_lat")
    private Double coordonneesDepartLat;

    @Column(name = "coordonnees_depart_lng")
    private Double coordonneesDepartLng;

    @Column(name = "coordonnees_arrivee_lat")
    private Double coordonneesArriveeLat;

    @Column(name = "coordonnees_arrivee_lng")
    private Double coordonneesArriveeLng;

    @Column(name = "coordonnees_temps_reel", columnDefinition = "jsonb", nullable = true)
    private String coordonneesTempsReel;

    @Column(name = "date_prise_charge")
    private LocalDateTime datePriseCharge;

    @Column(name = "date_livraison_prevue")
    private LocalDateTime dateLivraisonPrevue;

    @Column(name = "date_livraison_reelle")
    private LocalDateTime dateLivraisonReelle;

    @Column(name = "code_suivi")
    private String codeSuivi;

    @Column(name = "notes_livraison")
    private String notesLivraison;
}