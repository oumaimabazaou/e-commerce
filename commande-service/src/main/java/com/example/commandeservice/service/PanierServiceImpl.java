package com.example.commandeservice.service;

import com.example.commandeservice.entity.Panier;
import com.example.commandeservice.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class PanierServiceImpl implements PanierService {
    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private RestTemplate restTemplate;

    // ✅ URL du service boutique via API Gateway
    private static final String BOUTIQUE_SERVICE_URL = "http://localhost:8099/boutique-service";

    @Override
    @Transactional
    public Panier addToPanier(Panier panier) {
        // ✅ ENRICHIR LES DONNÉES PRODUIT AVANT SAUVEGARDE
        try {
            // Appeler le service boutique pour récupérer les infos du produit
            String productUrl = BOUTIQUE_SERVICE_URL + "/api/produits/" + panier.getIdProduit();
            Map<String, Object> productData = restTemplate.getForObject(productUrl, Map.class);

            if (productData != null) {
                // ✅ ENRICHIR LE PANIER AVEC LES VRAIES DONNÉES
                panier.setNomProduit((String) productData.get("nomProduit"));
                panier.setPrix(((Number) productData.get("prix")).doubleValue());
                panier.setImageUrlrecoit((String) productData.get("imageUrl"));
                panier.setCategorie((String) productData.get("idCategorie"));
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Erreur récupération produit: " + e.getMessage());
            // Continuer avec les données partielles
        }

        panier.setCreatedAt(LocalDateTime.now());
        panier.setUpdatedAt(LocalDateTime.now());
        return panierRepository.save(panier);
    }

    @Override
    public List<Panier> getPaniersByClient(String clientId) {
        List<Panier> paniers = panierRepository.findByIdClient(clientId);

        // ✅ ENRICHIR CHAQUE ARTICLE DU PANIER
        for (Panier panier : paniers) {
            enrichirPanierAvecDonneesProduit(panier);
        }

        return paniers;
    }

    // ✅ MÉTHODE POUR ENRICHIR UN ARTICLE DU PANIER
    private void enrichirPanierAvecDonneesProduit(Panier panier) {
        try {
            String productUrl = BOUTIQUE_SERVICE_URL + "/api/produits/" + panier.getIdProduit();
            Map<String, Object> productData = restTemplate.getForObject(productUrl, Map.class);

            if (productData != null) {
                // ✅ METTRE À JOUR AVEC LES DONNÉES ACTUELLES DU PRODUIT
                panier.setNomProduit((String) productData.get("nomProduit"));
                panier.setPrix(((Number) productData.get("prix")).doubleValue());
                panier.setImageUrlrecoit((String) productData.get("imageUrl"));
                panier.setCategorie((String) productData.get("idCategorie"));
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Erreur enrichissement panier: " + e.getMessage());
            // Garder les données existantes du panier
        }
    }

    // ✅ AUTRES MÉTHODES INCHANGÉES
    @Override
    public Optional<Panier> getPanierById(Integer id) {
        Optional<Panier> panier = panierRepository.findById(id);
        panier.ifPresent(this::enrichirPanierAvecDonneesProduit);
        return panier;
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
    @Override
    public List<Panier> getAllPaniers() {
        return panierRepository.findAll();
    }
}