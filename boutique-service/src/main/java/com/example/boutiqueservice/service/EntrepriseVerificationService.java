package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.EntrepriseVerification;
import com.example.boutiqueservice.repository.EntrepriseVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseVerificationService {
    private static final Logger logger = LoggerFactory.getLogger(EntrepriseVerificationService.class);

    @Autowired
    private EntrepriseVerificationRepository entrepriseVerificationRepository;

    public EntrepriseVerification createEntrepriseVerification(EntrepriseVerification entrepriseVerification) {
        if (entrepriseVerification == null || entrepriseVerification.getNom() == null || entrepriseVerification.getNom().trim().isEmpty() ||
                entrepriseVerification.getAdresse() == null || entrepriseVerification.getAdresse().trim().isEmpty()) {
            throw new IllegalArgumentException("nom et adresse sont requis et ne peuvent pas être vides.");
        }
        entrepriseVerification.setCreatedAt(LocalDateTime.now());
        logger.info("Tentative de création d'une vérification pour nom: {}", entrepriseVerification.getNom());
        try {
            return entrepriseVerificationRepository.save(entrepriseVerification);
        } catch (Exception e) {
            logger.error("Erreur lors de la création : {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<EntrepriseVerification> getEntrepriseVerificationById(Integer id) {
        return entrepriseVerificationRepository.findById(id);
    }

    public List<EntrepriseVerification> getAllEntrepriseVerifications() {
        return entrepriseVerificationRepository.findAll();
    }

    public EntrepriseVerification updateEntrepriseVerification(Integer id, EntrepriseVerification details) {
        EntrepriseVerification entrepriseVerification = entrepriseVerificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EntrepriseVerification not found"));
        if (details.getNom() != null && !details.getNom().trim().isEmpty()) entrepriseVerification.setNom(details.getNom());
        if (details.getAdresse() != null && !details.getAdresse().trim().isEmpty()) entrepriseVerification.setAdresse(details.getAdresse());
        if (details.getEmailContact() != null) entrepriseVerification.setEmailContact(details.getEmailContact());
        if (details.getTelephoneContact() != null) entrepriseVerification.setTelephoneContact(details.getTelephoneContact());
        if (details.getPays() != null) entrepriseVerification.setPays(details.getPays());
        if (details.getDocumentsRequis() != null) entrepriseVerification.setDocumentsRequis(details.getDocumentsRequis());
        return entrepriseVerificationRepository.save(entrepriseVerification);
    }

    public void deleteEntrepriseVerification(Integer id) {
        if (!entrepriseVerificationRepository.existsById(id)) {
            throw new RuntimeException("EntrepriseVerification not found");
        }
        entrepriseVerificationRepository.deleteById(id);
    }
}