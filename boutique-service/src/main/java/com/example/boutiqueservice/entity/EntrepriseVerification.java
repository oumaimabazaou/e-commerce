package com.example.boutiqueservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entreprise_verification")
public class EntrepriseVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrepriseVerif;

    private String nom;

    private String adresse;

    @Column(name = "email_contact")
    private String emailContact;

    @Column(name = "telephone_contact")
    private String telephoneContact;

    private String pays;

    @Column(columnDefinition = "JSON")
    private String documentsRequis;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Integer getIdEntrepriseVerif() {
        return idEntrepriseVerif;
    }

    public void setIdEntrepriseVerif(Integer idEntrepriseVerif) {
        this.idEntrepriseVerif = idEntrepriseVerif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getTelephoneContact() {
        return telephoneContact;
    }

    public void setTelephoneContact(String telephoneContact) {
        this.telephoneContact = telephoneContact;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDocumentsRequis() {
        return documentsRequis;
    }

    public void setDocumentsRequis(String documentsRequis) {
        this.documentsRequis = documentsRequis;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}