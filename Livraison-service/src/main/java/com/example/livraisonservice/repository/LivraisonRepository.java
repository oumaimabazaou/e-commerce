package com.example.livraisonservice.repository;

import com.example.livraisonservice.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivraisonRepository extends JpaRepository<Livraison, Integer> {
    List<Livraison> findByIdCommande(Integer idCommande);
    List<Livraison> findByIdLivreur(String idLivreur);
    List<Livraison> findByEntrepriseLivraisonIdEntreprise(Integer idEntreprise);
}