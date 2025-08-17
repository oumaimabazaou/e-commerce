package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.Publicite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PubliciteRepository extends JpaRepository<Publicite, Integer> {
    List<Publicite> findByIdBoutiqueAndActiveTrue(Integer idBoutique);
}