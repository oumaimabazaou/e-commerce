package com.example.evaluationservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluation;

    private Integer idUtilisateur;
    private Integer idEntiteEvaluee; // Référence à une entité évaluée (ex. EntrepriseLivraison)
    private Integer note; // De 1 à 5
    private String commentaire;
    private LocalDateTime dateEvaluation;
}