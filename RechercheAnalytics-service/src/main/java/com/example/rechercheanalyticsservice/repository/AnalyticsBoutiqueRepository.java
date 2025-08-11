package com.example.rechercheanalyticsservice.repository;

import com.example.rechercheanalyticsservice.entity.AnalyticsBoutique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyticsBoutiqueRepository extends JpaRepository<AnalyticsBoutique, Integer> {
    List<AnalyticsBoutique> findByIdBoutique(Integer idBoutique); // Nouvelle méthode
}