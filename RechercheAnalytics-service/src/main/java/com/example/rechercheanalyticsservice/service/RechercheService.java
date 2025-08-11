package com.example.rechercheanalyticsservice.service;

import com.example.rechercheanalyticsservice.entity.Recherche;
import com.example.rechercheanalyticsservice.repository.RechercheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RechercheService {
    @Autowired
    private RechercheRepository rechercheRepository;

    @Transactional
    public Recherche createRecherche(Recherche recherche) {
        if (recherche.getIdUtilisateur() == null) {
            throw new IllegalArgumentException("idUtilisateur est requis");
        }
        if (recherche.getTermeRecherche() == null || recherche.getTermeRecherche().trim().isEmpty()) {
            throw new IllegalArgumentException("termeRecherche est requis");
        }
        recherche.setDateRecherche(LocalDateTime.now());
        if (recherche.getResultats() == null) {
            recherche.setResultats("[]"); // Défaut : liste vide
        }
        return rechercheRepository.save(recherche);
    }

    public Optional<Recherche> getRechercheById(Integer id) {
        return rechercheRepository.findById(id);
    }

    public List<Recherche> getRecherchesByUtilisateur(Integer idUtilisateur) {
        return rechercheRepository.findByIdUtilisateur(idUtilisateur); // À implémenter dans le repository
    }
}