package com.example.paiementabonnementservice.repository;

import com.example.paiementabonnementservice.entity.Fiscalite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiscaliteRepository extends JpaRepository<Fiscalite, Integer> {
    List<Fiscalite> findByPaiementId(Integer idPaiement);
}