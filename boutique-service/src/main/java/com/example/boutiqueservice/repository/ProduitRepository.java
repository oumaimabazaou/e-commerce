package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByIdBoutiqueAndActifTrue(Integer idBoutique);
    List<Produit> findByIdCategorieAndActifTrue(String idCategorie);
}