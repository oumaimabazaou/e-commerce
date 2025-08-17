package com.example.paiementabonnementservice.service;

import com.example.paiementabonnementservice.entity.Fiscalite;
import com.example.paiementabonnementservice.entity.Paiement; // Ajout de l'import
import com.example.paiementabonnementservice.repository.FiscaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FiscaliteService {
    @Autowired
    private FiscaliteRepository fiscaliteRepository;

    @Transactional
    public Fiscalite createFiscalite(Fiscalite fiscalite) {
        // Vérifier la relation avec Paiement
        if (fiscalite.getPaiement() == null || fiscalite.getPaiement().getId() == null) {
            throw new IllegalArgumentException("paiement.id est requis");
        }
        if (fiscalite.getTauxTaxe() == null || fiscalite.getTauxTaxe() < 0) {
            throw new IllegalArgumentException("tauxTaxe doit être un nombre positif");
        }
        if (fiscalite.getMontantTaxe() == null || fiscalite.getMontantTaxe() < 0) {
            throw new IllegalArgumentException("montantTaxe doit être un nombre positif");
        }
        if (fiscalite.getTypeTaxe() == null || fiscalite.getTypeTaxe().trim().isEmpty()) {
            throw new IllegalArgumentException("typeTaxe est requis");
        }
        return fiscaliteRepository.save(fiscalite);
    }

    public Optional<Fiscalite> getFiscaliteById(Integer id) {
        return fiscaliteRepository.findById(id);
    }

    public List<Fiscalite> getFiscalitesByPaiement(Integer idPaiement) {
        // Utiliser la méthode du repository pour filtrer par idPaiement
        return fiscaliteRepository.findByPaiementId(idPaiement); // Utilise la méthode ajoutée dans le repository
    }
}