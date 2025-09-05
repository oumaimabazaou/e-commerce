package com.example.ecommerce.userservice.dto;

public class BoutiqueDTO {
    private Long idVendeur;
    private String nomBoutique;
    private String adresse;
    private String ville;
    private Boolean active;

    // Constructeurs
    public BoutiqueDTO() {
    }

    public BoutiqueDTO(Long idVendeur, String nomBoutique, String adresse, String ville, Boolean active) {
        this.idVendeur = idVendeur;
        this.nomBoutique = nomBoutique;
        this.adresse = adresse;
        this.ville = ville;
        this.active = active;
    }

    // Getters et Setters
    public Long getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(Long idVendeur) {
        this.idVendeur = idVendeur;
    }

    public String getNomBoutique() {
        return nomBoutique;
    }

    public void setNomBoutique(String nomBoutique) {
        this.nomBoutique = nomBoutique;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}