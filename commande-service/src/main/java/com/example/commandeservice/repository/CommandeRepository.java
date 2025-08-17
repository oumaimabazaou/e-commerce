package com.example.commandeservice.repository;

import com.example.commandeservice.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByStatut(String statut);

    List<Commande> findByIdClient(String clientId);
}