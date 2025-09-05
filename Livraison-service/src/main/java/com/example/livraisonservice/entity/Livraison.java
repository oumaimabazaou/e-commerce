package com.example.livraisonservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLivraison;

    @Column(name = "id_commande", nullable = false)
    private Integer idCommande;

    @Column(name = "id_livreur", nullable = false)
    private String idLivreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entreprise_livraison", nullable = false)
    private EntrepriseLivraison entrepriseLivraison;

    @Column(name = "type_livraison")
    private String typeLivraison;

    @Column(name = "prix_livraison")
    private Double prixLivraison;

    @Column(name = "statut_livraison")
    private String statutLivraison;

    @Column(name = "adresse_depart")
    private String adresseDepart;

    @Column(name = "adresse_arrivee")
    private String adresseArrivee;

    @Column(name = "coordonnees_depart_lat")
    private Double coordonneesDepartLat;

    @Column(name = "coordonnees_depart_lng")
    private Double coordonneesDepartLng;

    @Column(name = "coordonnees_arrivee_lat")
    private Double coordonneesArriveeLat;

    @Column(name = "coordonnees_arrivee_lng")
    private Double coordonneesArriveeLng;

    @Column(name = "coordonnees_temps_reel", columnDefinition = "jsonb", nullable = true)
    private String coordonneesTempsReel;

    @Column(name = "date_prise_charge")
    private LocalDateTime datePriseCharge;

    @Column(name = "date_livraison_prevue")
    private LocalDateTime dateLivraisonPrevue;

    @Column(name = "date_livraison_reelle")
    private LocalDateTime dateLivraisonReelle;

    @Column(name = "code_suivi")
    private String codeSuivi;

    @Column(name = "notes_livraison")
    private String notesLivraison;

    // Getters and Setters
    public Integer getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(Integer idLivraison) {
        this.idLivraison = idLivraison;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public String getIdLivreur() {
        return idLivreur;
    }

    public void setIdLivreur(String idLivreur) {
        this.idLivreur = idLivreur;
    }

    public EntrepriseLivraison getEntrepriseLivraison() {
        return entrepriseLivraison;
    }

    public void setEntrepriseLivraison(EntrepriseLivraison entrepriseLivraison) {
        this.entrepriseLivraison = entrepriseLivraison;
    }

    public String getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(String typeLivraison) {
        this.typeLivraison = typeLivraison;
    }

    public Double getPrixLivraison() {
        return prixLivraison;
    }

    public void setPrixLivraison(Double prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    public String getStatutLivraison() {
        return statutLivraison;
    }

    public void setStatutLivraison(String statutLivraison) {
        this.statutLivraison = statutLivraison;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public Double getCoordonneesDepartLat() {
        return coordonneesDepartLat;
    }

    public void setCoordonneesDepartLat(Double coordonneesDepartLat) {
        this.coordonneesDepartLat = coordonneesDepartLat;
    }

    public Double getCoordonneesDepartLng() {
        return coordonneesDepartLng;
    }

    public void setCoordonneesDepartLng(Double coordonneesDepartLng) {
        this.coordonneesDepartLng = coordonneesDepartLng;
    }

    public Double getCoordonneesArriveeLat() {
        return coordonneesArriveeLat;
    }

    public void setCoordonneesArriveeLat(Double coordonneesArriveeLat) {
        this.coordonneesArriveeLat = coordonneesArriveeLat;
    }

    public Double getCoordonneesArriveeLng() {
        return coordonneesArriveeLng;
    }

    public void setCoordonneesArriveeLng(Double coordonneesArriveeLng) {
        this.coordonneesArriveeLng = coordonneesArriveeLng;
    }

    public String getCoordonneesTempsReel() {
        return coordonneesTempsReel;
    }

    public void setCoordonneesTempsReel(String coordonneesTempsReel) {
        this.coordonneesTempsReel = coordonneesTempsReel;
    }

    public LocalDateTime getDatePriseCharge() {
        return datePriseCharge;
    }

    public void setDatePriseCharge(LocalDateTime datePriseCharge) {
        this.datePriseCharge = datePriseCharge;
    }

    public LocalDateTime getDateLivraisonPrevue() {
        return dateLivraisonPrevue;
    }

    public void setDateLivraisonPrevue(LocalDateTime dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    public LocalDateTime getDateLivraisonReelle() {
        return dateLivraisonReelle;
    }

    public void setDateLivraisonReelle(LocalDateTime dateLivraisonReelle) {
        this.dateLivraisonReelle = dateLivraisonReelle;
    }

    public String getCodeSuivi() {
        return codeSuivi;
    }

    public void setCodeSuivi(String codeSuivi) {
        this.codeSuivi = codeSuivi;
    }

    public String getNotesLivraison() {
        return notesLivraison;
    }

    public void setNotesLivraison(String notesLivraison) {
        this.notesLivraison = notesLivraison;
    }
}