package com.example.commandeservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "litige")
public class Litige {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLitige;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @Column(name = "description")
    private String description;

    @Column(name = "statut")
    private String statut;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_resolution")
    private LocalDateTime dateResolution;
}