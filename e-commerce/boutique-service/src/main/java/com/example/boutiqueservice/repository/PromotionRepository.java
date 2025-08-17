package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByIdBoutiqueAndActiveTrue(Integer idBoutique);
}