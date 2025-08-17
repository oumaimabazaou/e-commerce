package com.example.commandeservice.repository;

import com.example.commandeservice.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Integer> {
    List<LigneCommande> findByCommande_IdCommande(Integer commandeId);
}