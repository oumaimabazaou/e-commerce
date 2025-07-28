package com.example.ecommerce.userservice.repository;

import com.example.ecommerce.userservice.entity.Vendeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendeurRepository extends JpaRepository<Vendeur, String> {
} 