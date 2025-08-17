package com.example.commandeservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "panier")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPanier;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "id_client")
    private String idClient;

    @Column(name = "nom_produit")
    private String nomProduit;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "image_urlrecoit")
    private String imageUrlrecoit;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}