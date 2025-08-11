package com.example.commandeservice.service;

import com.example.commandeservice.entity.Panier;
import java.util.List;
import java.util.Optional;

public interface PanierService {
    Panier addToPanier(Panier panier);
    Optional<Panier> getPanierById(Integer id);
    List<Panier> getPaniersByClient(String clientId);
    Panier updatePanier(Integer id, Panier panier);
    void removeFromPanier(Integer id);
    void clearPanier(String clientId);
}