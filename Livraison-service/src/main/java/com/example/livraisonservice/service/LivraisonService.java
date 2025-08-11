package com.example.livraisonservice.service;

import com.example.livraisonservice.entity.Livraison;
import com.example.livraisonservice.entity.EntrepriseLivraison;
import com.example.livraisonservice.repository.LivraisonRepository;
import com.example.livraisonservice.repository.EntrepriseLivraisonRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivraisonService {
    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private EntrepriseLivraisonRepository entrepriseLivraisonRepository;

    @Transactional
    public Livraison createLivraison(Livraison livraison) {
        try {
            if (livraison.getIdCommande() == null) {
                throw new IllegalArgumentException("idCommande est requis");
            }
            if (livraison.getIdLivreur() == null) {
                throw new IllegalArgumentException("idLivreur est requis");
            }
            if (livraison.getEntrepriseLivraison() == null || livraison.getEntrepriseLivraison().getIdEntreprise() == null) {
                throw new IllegalArgumentException("entrepriseLivraison.idEntreprise est requis");
            }

            Integer idEntreprise = livraison.getEntrepriseLivraison().getIdEntreprise();
            EntrepriseLivraison entreprise = entrepriseLivraisonRepository.findById(idEntreprise)
                    .orElseThrow(() -> new IllegalArgumentException("Entreprise avec ID " + idEntreprise + " non trouvée"));

            livraison.setEntrepriseLivraison(entreprise);
            livraison.setDatePriseCharge(LocalDateTime.now());
            livraison.setStatutLivraison("En attente");

            if (livraison.getCoordonneesTempsReel() != null && !livraison.getCoordonneesTempsReel().trim().isEmpty()) {
                try {
                    new JSONObject(livraison.getCoordonneesTempsReel());
                } catch (JSONException e) {
                    throw new IllegalArgumentException("coordonneesTempsReel doit être un JSON valide", e);
                }
            }

            return livraisonRepository.save(livraison);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la livraison : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la création de la livraison", e);
        }
    }

    public Optional<Livraison> getLivraisonById(Integer id) {
        return livraisonRepository.findById(id);
    }

    public List<Livraison> getLivraisonsByCommande(Integer idCommande) {
        return livraisonRepository.findByIdCommande(idCommande);
    }

    public List<Livraison> getLivraisonsByLivreur(String idLivreur) {
        return livraisonRepository.findByIdLivreur(idLivreur);
    }

    public List<Livraison> getLivraisonsByEntreprise(Integer idEntreprise) {
        return livraisonRepository.findByEntrepriseLivraisonIdEntreprise(idEntreprise);
    }

    @Transactional
    public Livraison updateLivraison(Integer id, Livraison livraison) {
        Livraison existing = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
        if (livraison.getIdCommande() != null) existing.setIdCommande(livraison.getIdCommande());
        if (livraison.getIdLivreur() != null) existing.setIdLivreur(livraison.getIdLivreur());
        if (livraison.getEntrepriseLivraison() != null && livraison.getEntrepriseLivraison().getIdEntreprise() != null) {
            EntrepriseLivraison entreprise = entrepriseLivraisonRepository.findById(livraison.getEntrepriseLivraison().getIdEntreprise())
                    .orElseThrow(() -> new IllegalArgumentException("Entreprise non trouvée"));
            existing.setEntrepriseLivraison(entreprise);
        }
        if (livraison.getTypeLivraison() != null) existing.setTypeLivraison(livraison.getTypeLivraison());
        if (livraison.getPrixLivraison() != null) existing.setPrixLivraison(livraison.getPrixLivraison());
        if (livraison.getStatutLivraison() != null) existing.setStatutLivraison(livraison.getStatutLivraison());
        if (livraison.getAdresseDepart() != null) existing.setAdresseDepart(livraison.getAdresseDepart());
        if (livraison.getAdresseArrivee() != null) existing.setAdresseArrivee(livraison.getAdresseArrivee());
        if (livraison.getCoordonneesDepartLat() != null) existing.setCoordonneesDepartLat(livraison.getCoordonneesDepartLat());
        if (livraison.getCoordonneesDepartLng() != null) existing.setCoordonneesDepartLng(livraison.getCoordonneesDepartLng());
        if (livraison.getCoordonneesArriveeLat() != null) existing.setCoordonneesArriveeLat(livraison.getCoordonneesArriveeLat());
        if (livraison.getCoordonneesArriveeLng() != null) existing.setCoordonneesArriveeLng(livraison.getCoordonneesArriveeLng());
        if (livraison.getCoordonneesTempsReel() != null) {
            if (!livraison.getCoordonneesTempsReel().trim().isEmpty()) {
                try {
                    new JSONObject(livraison.getCoordonneesTempsReel());
                } catch (JSONException e) {
                    throw new IllegalArgumentException("coordonneesTempsReel doit être un JSON valide", e);
                }
            }
            existing.setCoordonneesTempsReel(livraison.getCoordonneesTempsReel());
        }
        if (livraison.getDatePriseCharge() != null) existing.setDatePriseCharge(livraison.getDatePriseCharge());
        if (livraison.getDateLivraisonPrevue() != null) existing.setDateLivraisonPrevue(livraison.getDateLivraisonPrevue());
        if (livraison.getDateLivraisonReelle() != null) existing.setDateLivraisonReelle(livraison.getDateLivraisonReelle());
        if (livraison.getCodeSuivi() != null) existing.setCodeSuivi(livraison.getCodeSuivi());
        if (livraison.getNotesLivraison() != null) existing.setNotesLivraison(livraison.getNotesLivraison());
        return livraisonRepository.save(existing);
    }

    @Transactional
    public void deleteLivraison(Integer id) {
        livraisonRepository.deleteById(id);
    }
}