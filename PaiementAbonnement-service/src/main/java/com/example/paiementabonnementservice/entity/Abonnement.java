package com.example.paiementabonnementservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "abonnement")
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAbonnement;

    private String typeAbonnement;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String statutAbonnement;
    private Integer idUtilisateur; // Référence à un utilisateur (à valider avec un autre service si intégré)
}