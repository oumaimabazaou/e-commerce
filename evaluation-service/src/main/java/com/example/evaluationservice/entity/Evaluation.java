package com.example.evaluationservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    // Getters and Setters
    public Integer getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Integer idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdEntiteEvaluee() {
        return idEntiteEvaluee;
    }

    public void setIdEntiteEvaluee(Integer idEntiteEvaluee) {
        this.idEntiteEvaluee = idEntiteEvaluee;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(LocalDateTime dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }
}