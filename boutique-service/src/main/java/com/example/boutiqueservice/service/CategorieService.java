package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Categorie;
import com.example.boutiqueservice.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Optional<Categorie> getCategorieById(String id) {
        return categorieRepository.findById(id);
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie updateCategorie(String id, Categorie details) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found"));
        categorie.setNomCategorie(details.getNomCategorie());
        categorie.setDescription(details.getDescription());
        categorie.setIdParent(details.getIdParent());
        categorie.setIcon(details.getIcon());
        categorie.setCustomIcon(details.getCustomIcon());
        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(String id) {
        categorieRepository.deleteById(id);
    }
}