package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Promotion;
import com.example.boutiqueservice.repository.PromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    private static final Logger logger = LoggerFactory.getLogger(PromotionService.class);

    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion createPromotion(Promotion promotion) {
        // Validation des champs obligatoires
        if (promotion == null || promotion.getIdBoutique() == null || promotion.getNom() == null || promotion.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("idBoutique et nom sont requis et ne peuvent pas être vides.");
        }
        // Validation supplémentaire
        if (promotion.getPourcentage() == null) {
            throw new IllegalArgumentException("Le pourcentage est requis.");
        }
        if (promotion.getPourcentage() < 0 || promotion.getPourcentage() > 100) {
            throw new IllegalArgumentException("Le pourcentage doit être entre 0 et 100.");
        }

        // Initialisation des valeurs par défaut
        if (promotion.getDateDebut() == null) promotion.setDateDebut(LocalDateTime.now());
        if (promotion.getDateFin() == null) promotion.setDateFin(LocalDateTime.now().plusDays(30)); // Défaut : 30 jours
        if (promotion.getActive() == null) promotion.setActive(true);
        if (promotion.getUtilisationsMax() == null) promotion.setUtilisationsMax(0);
        if (promotion.getUtilisationsActuelles() == null) promotion.setUtilisationsActuelles(0);

        logger.info("Tentative de création d'une promotion pour nom: {}", promotion.getNom());
        try {
            return promotionRepository.save(promotion);
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur de contrainte de données : {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la création : {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Promotion> getPromotionById(Integer id) {
        return promotionRepository.findById(id);
    }

    public List<Promotion> getPromotionsByBoutique(Integer idBoutique) {
        return promotionRepository.findByIdBoutiqueAndActiveTrue(idBoutique);
    }

    public Promotion updatePromotion(Integer id, Promotion promotionDetails) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        if (promotionDetails.getIdBoutique() != null) promotion.setIdBoutique(promotionDetails.getIdBoutique());
        if (promotionDetails.getNom() != null && !promotionDetails.getNom().trim().isEmpty()) promotion.setNom(promotionDetails.getNom());
        if (promotionDetails.getDescription() != null) promotion.setDescription(promotionDetails.getDescription());
        if (promotionDetails.getPourcentage() != null && promotionDetails.getPourcentage() >= 0 && promotionDetails.getPourcentage() <= 100) {
            promotion.setPourcentage(promotionDetails.getPourcentage());
        }
        if (promotionDetails.getDateDebut() != null) promotion.setDateDebut(promotionDetails.getDateDebut());
        if (promotionDetails.getDateFin() != null) promotion.setDateFin(promotionDetails.getDateFin());
        if (promotionDetails.getProduitsEligibles() != null) promotion.setProduitsEligibles(promotionDetails.getProduitsEligibles());
        if (promotionDetails.getConditionsUtilisation() != null) promotion.setConditionsUtilisation(promotionDetails.getConditionsUtilisation());
        if (promotionDetails.getUtilisationsMax() != null) promotion.setUtilisationsMax(promotionDetails.getUtilisationsMax());
        if (promotionDetails.getUtilisationsActuelles() != null) promotion.setUtilisationsActuelles(promotionDetails.getUtilisationsActuelles());
        if (promotionDetails.getActive() != null) promotion.setActive(promotionDetails.getActive());
        return promotionRepository.save(promotion);
    }

    public void deletePromotion(Integer id) {
        if (!promotionRepository.existsById(id)) {
            throw new RuntimeException("Promotion not found");
        }
        promotionRepository.deleteById(id);
    }
}