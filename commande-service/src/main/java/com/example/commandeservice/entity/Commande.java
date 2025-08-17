package com.example.commandeservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCommande;

    @Column(name = "id_client")
    private String idClient;

    @Column(name = "id_boutique")
    private Integer idBoutique;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "total")
    private Double total;

    @Column(name = "statut")
    private String statut;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "avec_livraison")
    private Boolean avecLivraison;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "coordonnees_livraison_lat")
    private Double coordonneesLivraisonLat;

    @Column(name = "coordonnees_livraison_lng")
    private Double coordonneesLivraisonLng;

    @Column(name = "delivery_notes")
    private String deliveryNotes;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> ligneCommandes;

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private ShippingInfo shippingInfo;
}