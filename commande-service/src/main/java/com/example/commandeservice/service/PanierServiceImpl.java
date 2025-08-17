package com.example.commandeservice.service;

import com.example.commandeservice.entity.Panier;
import com.example.commandeservice.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PanierServiceImpl implements PanierService {
    @Autowired
    private PanierRepository panierRepository;

    @Override
    @Transactional
    public Panier addToPanier(Panier panier) {
        panier.setCreatedAt(LocalDateTime.now());
        panier.setUpdatedAt(LocalDateTime.now());
        return panierRepository.save(panier);
    }

    @Override
    public Optional<Panier> getPanierById(Integer id) {
        return panierRepository.findById(id);
    }

    @Override
    public List<Panier> getPaniersByClient(String clientId) {
        return panierRepository.findByIdClient(clientId);
    }

    @Override
    @Transactional
    public Panier updatePanier(Integer id, Panier panier) {
        Panier existing = panierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        existing.setQuantite(panier.getQuantite());
        existing.setUpdatedAt(LocalDateTime.now());
        return panierRepository.save(existing);
    }

    @Override
    public void removeFromPanier(Integer id) {
        panierRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void clearPanier(String clientId) {
        List<Panier> paniers = panierRepository.findByIdClient(clientId);
        panierRepository.deleteAll(paniers);
    }
}