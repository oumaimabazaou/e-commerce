package com.example.paiementabonnementservice.service;

import com.example.paiementabonnementservice.entity.Paiement;
import com.example.paiementabonnementservice.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {
    @Autowired
    private PaiementRepository paiementRepository;

    @Transactional
    public Paiement createPaiement(Paiement paiement) {
        if (paiement.getIdAbonnement() == null) {
            throw new IllegalArgumentException("idAbonnement est requis");
        }
        if (paiement.getMontant() == null || paiement.getMontant() <= 0) {
            throw new IllegalArgumentException("montant doit être positif");
        }
        paiement.setDatePaiement(LocalDateTime.now());
        paiement.setStatutPaiement("En attente");
        return paiementRepository.save(paiement);
    }

    public Optional<Paiement> getPaiementById(Integer id) {
        return paiementRepository.findById(id);
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public List<Paiement> getPaiementsByAbonnement(Integer idAbonnement) {
        return paiementRepository.findByIdAbonnement(idAbonnement);
    }

    public void markPayment(String idCommande) {
        System.out.println("Paiement marqué pour la commande : " + idCommande);
    }
}