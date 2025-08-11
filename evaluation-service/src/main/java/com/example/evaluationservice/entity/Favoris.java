package com.example.evaluationservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "favoris")
public class Favoris {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavori;

    private Integer idUtilisateur;
    private Integer idEntiteFavoree; // Référence à une entité favorisée (ex. EntrepriseLivraison)
    private LocalDateTime dateAjout;
}