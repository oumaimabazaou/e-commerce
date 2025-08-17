package com.example.commandeservice.repository;

import com.example.commandeservice.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
    List<Panier> findByIdClient(String clientId);
}