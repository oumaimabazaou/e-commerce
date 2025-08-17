package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Promotion;
import com.example.boutiqueservice.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
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
        promotion.setIdBoutique(promotionDetails.getIdBoutique());
        promotion.setNom(promotionDetails.getNom());
        promotion.setDescription(promotionDetails.getDescription());
        promotion.setPourcentage(promotionDetails.getPourcentage());
        promotion.setDateDebut(promotionDetails.getDateDebut());
        promotion.setDateFin(promotionDetails.getDateFin());
        promotion.setProduitsEligibles(promotionDetails.getProduitsEligibles());
        promotion.setConditionsUtilisation(promotionDetails.getConditionsUtilisation());
        promotion.setUtilisationsMax(promotionDetails.getUtilisationsMax());
        promotion.setUtilisationsActuelles(promotionDetails.getUtilisationsActuelles());
        promotion.setActive(promotionDetails.getActive());
        return promotionRepository.save(promotion);
    }

    public void deletePromotion(Integer id) {
        promotionRepository.deleteById(id);
    }
}