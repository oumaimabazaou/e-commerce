package com.example.ecommerce.userservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "id_client")
    private String idClient;

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Lob
    private String preferences; // JSON
    @Lob
    private String historiqueRecherche; // JSON
    private Boolean notificationsActivees;
    // Getters, setters, constructeurs

    public String getIdClient() { return idClient; }
    public void setIdClient(String idClient) { this.idClient = idClient; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
    public String getHistoriqueRecherche() { return historiqueRecherche; }
    public void setHistoriqueRecherche(String historiqueRecherche) { this.historiqueRecherche = historiqueRecherche; }
    public Boolean getNotificationsActivees() { return notificationsActivees; }
    public void setNotificationsActivees(Boolean notificationsActivees) { this.notificationsActivees = notificationsActivees; }
} 