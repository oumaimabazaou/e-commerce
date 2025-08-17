package com.example.rechercheanalyticsservice.service;

import com.example.rechercheanalyticsservice.entity.AnalyticsBoutique;
import com.example.rechercheanalyticsservice.repository.AnalyticsBoutiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsBoutiqueService {
    @Autowired
    private AnalyticsBoutiqueRepository analyticsBoutiqueRepository;

    @Transactional
    public AnalyticsBoutique createAnalytics(AnalyticsBoutique analytics) {
        if (analytics.getIdBoutique() == null) {
            throw new IllegalArgumentException("idBoutique est requis");
        }
        if (analytics.getNombreVisites() == null || analytics.getNombreVisites() < 0) {
            throw new IllegalArgumentException("nombreVisites doit être positif");
        }
        if (analytics.getChiffreAffaires() == null || analytics.getChiffreAffaires() < 0) {
            throw new IllegalArgumentException("chiffreAffaires doit être positif");
        }
        analytics.setDateAnalyse(LocalDateTime.now());
        if (analytics.getPeriode() == null || analytics.getPeriode().trim().isEmpty()) {
            analytics.setPeriode("Mensuelle"); // Défaut si non fourni
        }
        return analyticsBoutiqueRepository.save(analytics);
    }

    public Optional<AnalyticsBoutique> getAnalyticsById(Integer id) {
        return analyticsBoutiqueRepository.findById(id);
    }

    public List<AnalyticsBoutique> getAnalyticsByBoutique(Integer idBoutique) {
        // Remplacer par une requête spécifique (nécessite une mise à jour du repository)
        return analyticsBoutiqueRepository.findByIdBoutique(idBoutique); // À implémenter dans le repository
    }
}