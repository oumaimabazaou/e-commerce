package com.example.commandeservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ligne_commande")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLigne;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "sous_total")
    private Double sousTotal;
}