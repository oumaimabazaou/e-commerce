package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.EntrepriseVerification;
import com.example.boutiqueservice.repository.EntrepriseVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseVerificationService {
    @Autowired
    private EntrepriseVerificationRepository entrepriseVerificationRepository;

    public EntrepriseVerification createEntrepriseVerification(EntrepriseVerification entrepriseVerification) {
        return entrepriseVerificationRepository.save(entrepriseVerification);
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
        entrepriseVerification.setNom(details.getNom());
        entrepriseVerification.setAdresse(details.getAdresse());
        entrepriseVerification.setEmailContact(details.getEmailContact());
        entrepriseVerification.setTelephoneContact(details.getTelephoneContact());
        entrepriseVerification.setPays(details.getPays());
        entrepriseVerification.setDocumentsRequis(details.getDocumentsRequis());
        return entrepriseVerificationRepository.save(entrepriseVerification);
    }

    public void deleteEntrepriseVerification(Integer id) {
        entrepriseVerificationRepository.deleteById(id);
    }
}