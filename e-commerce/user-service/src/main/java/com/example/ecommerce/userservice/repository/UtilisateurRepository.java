package com.example.ecommerce.userservice.repository;

import com.example.ecommerce.userservice.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
} 