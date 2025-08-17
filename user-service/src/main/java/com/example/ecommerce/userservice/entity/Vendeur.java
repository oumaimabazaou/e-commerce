package com.example.ecommerce.userservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendeur")
public class Vendeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendeur")
    private Long idVendeur; // Changé de String à Long

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    private String numeroIce;
    private String numeroPatente;
    private String statutFiscal;
    private BigDecimal chiffreAffaires;
    private LocalDateTime dateVerification;
    private Boolean verifie;

    // Constructeur par défaut
    public Vendeur() {}

    // Getters et setters
    public Long getIdVendeur() { return idVendeur; }
    public void setIdVendeur(Long idVendeur) { this.idVendeur = idVendeur; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public String getNumeroIce() { return numeroIce; }
    public void setNumeroIce(String numeroIce) { this.numeroIce = numeroIce; }
    public String getNumeroPatente() { return numeroPatente; }
    public void setNumeroPatente(String numeroPatente) { this.numeroPatente = numeroPatente; }
    public String getStatutFiscal() { return statutFiscal; }
    public void setStatutFiscal(String statutFiscal) { this.statutFiscal = statutFiscal; }
    public BigDecimal getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(BigDecimal chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }
    public LocalDateTime getDateVerification() { return dateVerification; }
    public void setDateVerification(LocalDateTime dateVerification) { this.dateVerification = dateVerification; }
    public Boolean getVerifie() { return verifie; }
    public void setVerifie(Boolean verifie) { this.verifie = verifie; }
}