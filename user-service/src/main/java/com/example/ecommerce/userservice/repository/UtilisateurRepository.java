package com.example.ecommerce.userservice.repository;

import com.example.ecommerce.userservice.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> { // <-- CORRECTION : Long au lieu de String
    Optional<Utilisateur> findByEmail(String email);
}