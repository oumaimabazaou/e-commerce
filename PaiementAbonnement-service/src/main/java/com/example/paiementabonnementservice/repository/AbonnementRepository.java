package com.example.paiementabonnementservice.repository;

import com.example.paiementabonnementservice.entity.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
}