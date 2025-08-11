package com.example.paiementabonnementservice.repository;

import com.example.paiementabonnementservice.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
    List<Paiement> findByIdAbonnement(Integer idAbonnement); // Nouvelle méthode
}