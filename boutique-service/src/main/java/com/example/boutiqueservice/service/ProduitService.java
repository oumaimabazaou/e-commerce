package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Produit;
import com.example.boutiqueservice.repository.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    private static final Logger logger = LoggerFactory.getLogger(ProduitService.class);

    @Autowired
    private ProduitRepository produitRepository;

    public Produit createProduit(Produit produit) {
        if (produit == null || produit.getIdBoutique() == null || produit.getIdCategorie() == null ||
                produit.getIdCategorie().trim().isEmpty() || produit.getNomProduit() == null || produit.getNomProduit().trim().isEmpty()) {
            throw new IllegalArgumentException("idBoutique, idCategorie et nomProduit sont requis et ne peuvent pas être vides.");
        }
        produit.setDateAjout(LocalDateTime.now());
        if (produit.getActif() == null) produit.setActif(true);
        logger.info("Tentative de création d'un produit pour nom: {}", produit.getNomProduit());
        try {
            return produitRepository.save(produit);
        } catch (Exception e) {
            logger.error("Erreur lors de la création : {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Produit> getProduitById(Integer id) {
        return produitRepository.findById(id);
    }

    public List<Produit> getProduitsByBoutique(Integer idBoutique) {
        return produitRepository.findByIdBoutiqueAndActifTrue(idBoutique);
    }

    public List<Produit> getProduitsByCategorie(String idCategorie) {
        return produitRepository.findByIdCategorieAndActifTrue(idCategorie);
    }

    public Produit updateProduit(Integer id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        if (produitDetails.getIdBoutique() != null) produit.setIdBoutique(produitDetails.getIdBoutique());
        if (produitDetails.getIdCategorie() != null && !produitDetails.getIdCategorie().trim().isEmpty()) produit.setIdCategorie(produitDetails.getIdCategorie());
        if (produitDetails.getNomProduit() != null && !produitDetails.getNomProduit().trim().isEmpty()) produit.setNomProduit(produitDetails.getNomProduit());
        if (produitDetails.getDescription() != null) produit.setDescription(produitDetails.getDescription());
        if (produitDetails.getDetail() != null) produit.setDetail(produitDetails.getDetail());
        if (produitDetails.getPrix() != null) produit.setPrix(produitDetails.getPrix());
        if (produitDetails.getCodeBarre() != null) produit.setCodeBarre(produitDetails.getCodeBarre());
        if (produitDetails.getPhotos() != null) produit.setPhotos(produitDetails.getPhotos());
        if (produitDetails.getVideos() != null) produit.setVideos(produitDetails.getVideos());
        if (produitDetails.getDimensions() != null) produit.setDimensions(produitDetails.getDimensions());
        if (produitDetails.getPoids() != null) produit.setPoids(produitDetails.getPoids());
        if (produitDetails.getDateExpiration() != null) produit.setDateExpiration(produitDetails.getDateExpiration());
        if (produitDetails.getActif() != null) produit.setActif(produitDetails.getActif());
        return produitRepository.save(produit);
    }

    public void deleteProduit(Integer id) {
        if (!produitRepository.existsById(id)) {
            throw new RuntimeException("Produit not found");
        }
        produitRepository.deleteById(id);
    }
}