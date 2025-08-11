package com.example.commandeservice.repository;

import com.example.commandeservice.entity.Litige;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LitigeRepository extends JpaRepository<Litige, Integer> {
    List<Litige> findByCommande_IdCommande(Integer commandeId);
}