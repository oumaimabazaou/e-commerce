package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique, Integer> {
    List<Boutique> findByIdVendeur(String idVendeur);
    List<Boutique> findByVilleAndActiveTrue(String ville);
}