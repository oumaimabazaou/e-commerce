package com.example.boutiqueservice.repository;

import com.example.boutiqueservice.entity.EntrepriseVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseVerificationRepository extends JpaRepository<EntrepriseVerification, Integer> {
}