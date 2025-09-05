package com.example.ecommerce.userservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendeur")
public class Vendeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendeur")
    private Long idVendeur;

    @OneToOne(cascade = CascadeType.ALL) // Optionnel : ajuste selon votre besoin
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    private String numeroIce;
    private String numeroPatente;
    private String statutFiscal;
    private Double chiffreAffaires; // Changer en Double si nécessaire
    private Boolean verifie;

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
    public Double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(Double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }
    public Boolean getVerifie() { return verifie; }
    public void setVerifie(Boolean verifie) { this.verifie = verifie; }
}