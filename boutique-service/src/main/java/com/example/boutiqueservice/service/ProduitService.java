package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Produit;
import com.example.boutiqueservice.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
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
        produit.setIdBoutique(produitDetails.getIdBoutique());
        produit.setIdCategorie(produitDetails.getIdCategorie());
        produit.setNomProduit(produitDetails.getNomProduit());
        produit.setDescription(produitDetails.getDescription());
        produit.setDetail(produitDetails.getDetail());
        produit.setPrix(produitDetails.getPrix());
        produit.setCodeBarre(produitDetails.getCodeBarre());
        produit.setPhotos(produitDetails.getPhotos());
        produit.setVideos(produitDetails.getVideos());
        produit.setDimensions(produitDetails.getDimensions());
        produit.setPoids(produitDetails.getPoids());
        produit.setDateExpiration(produitDetails.getDateExpiration());
        produit.setActif(produitDetails.getActif());
        return produitRepository.save(produit);
    }

    public void deleteProduit(Integer id) {
        produitRepository.deleteById(id);
    }
}