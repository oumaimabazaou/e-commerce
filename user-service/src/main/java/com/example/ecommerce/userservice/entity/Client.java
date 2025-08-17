package com.example.ecommerce.userservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient; // Changé de String à Long

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Lob
    private String preferences; // JSON
    @Lob
    private String historiqueRecherche; // JSON
    private Boolean notificationsActivees;

    // Constructeur par défaut
    public Client() {}

    // Getters et setters
    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
    public String getHistoriqueRecherche() { return historiqueRecherche; }
    public void setHistoriqueRecherche(String historiqueRecherche) { this.historiqueRecherche = historiqueRecherche; }
    public Boolean getNotificationsActivees() { return notificationsActivees; }
    public void setNotificationsActivees(Boolean notificationsActivees) { this.notificationsActivees = notificationsActivees; }
}