package com.example.livraisonservice.repository;

import com.example.livraisonservice.entity.EntrepriseLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseLivraisonRepository extends JpaRepository<EntrepriseLivraison, Integer> {
}