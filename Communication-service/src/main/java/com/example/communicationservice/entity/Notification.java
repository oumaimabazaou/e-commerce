package com.example.communicationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotification;

    @Column(nullable = false)
    @NotNull(message = "idUtilisateur est requis")
    private Integer idUtilisateur;

    @Column(nullable = false)
    @NotNull(message = "typeNotification est requis")
    private String typeNotification;

    @Column(nullable = false)
    @NotNull(message = "message est requis")
    private String message;

    private String titre;
    private String priorite;

    @Column(columnDefinition = "TEXT")
    private String data;

    private LocalDateTime dateNotification;
    private Boolean lu;

    // Getters and Setters
    public Integer getIdNotification() { return idNotification; }
    public void setIdNotification(Integer idNotification) { this.idNotification = idNotification; }

    public Integer getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Integer idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public String getTypeNotification() { return typeNotification; }
    public void setTypeNotification(String typeNotification) { this.typeNotification = typeNotification; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public LocalDateTime getDateNotification() { return dateNotification; }
    public void setDateNotification(LocalDateTime dateNotification) { this.dateNotification = dateNotification; }

    public Boolean getLu() { return lu; }
    public void setLu(Boolean lu) { this.lu = lu; }
}