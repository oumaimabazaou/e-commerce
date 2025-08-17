package com.example.paiementabonnementservice.service;

import com.example.paiementabonnementservice.entity.Abonnement;
import com.example.paiementabonnementservice.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {
    @Autowired
    private AbonnementRepository abonnementRepository;

    @Transactional
    public Abonnement createAbonnement(Abonnement abonnement) {
        if (abonnement.getTypeAbonnement() == null || abonnement.getTypeAbonnement().isEmpty()) {
            throw new IllegalArgumentException("typeAbonnement est requis");
        }
        abonnement.setDateDebut(LocalDateTime.now());
        abonnement.setDateFin(LocalDateTime.now().plusYears(1)); // Exemple : 1 an
        abonnement.setStatutAbonnement("Actif");
        return abonnementRepository.save(abonnement);
    }

    public Optional<Abonnement> getAbonnementById(Integer id) {
        return abonnementRepository.findById(id);
    }

    public List<Abonnement> getAbonnementsByUtilisateur(Integer idUtilisateur) {
        return abonnementRepository.findAll().stream()
                .filter(a -> a.getIdUtilisateur().equals(idUtilisateur))
                .toList();
    }
}