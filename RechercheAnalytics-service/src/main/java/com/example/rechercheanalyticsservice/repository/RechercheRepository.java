package com.example.rechercheanalyticsservice.repository;

import com.example.rechercheanalyticsservice.entity.Recherche;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechercheRepository extends JpaRepository<Recherche, Integer> {
    List<Recherche> findByIdUtilisateur(Integer idUtilisateur); // Nouvelle méthode
}