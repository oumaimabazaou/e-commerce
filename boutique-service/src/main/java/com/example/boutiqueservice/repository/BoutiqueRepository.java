package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique, Integer> {
    boolean existsByNomBoutiqueIgnoreCase(String nomBoutique);
    List<Boutique> findByIdVendeur(Long idVendeur); // Mis à jour pour Long
    List<Boutique> findByVilleAndActiveTrue(String ville);
    List<Boutique> findByActiveTrue();
}