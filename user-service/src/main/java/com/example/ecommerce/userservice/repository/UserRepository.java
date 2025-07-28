package com.example.ecommerce.userservice.repository;

import com.example.ecommerce.userservice.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    // Méthodes de recherche personnalisées si besoin
} 