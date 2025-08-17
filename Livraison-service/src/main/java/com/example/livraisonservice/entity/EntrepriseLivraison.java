package com.example.livraisonservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
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
}