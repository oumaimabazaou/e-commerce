package com.example.ecommerce.stockservice.service;

import com.example.ecommerce.stockservice.entity.Stock;
import com.example.ecommerce.stockservice.entity.StockTransaction;
import com.example.ecommerce.stockservice.repository.StockRepository;
import com.example.ecommerce.stockservice.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    // === GESTION DES STOCKS ===

    /**
     * Créer un nouveau stock
     */
    public Stock createStock(Stock stock) {
        stock.setDerniereMaj(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    /**
     * Obtenir un stock par son ID
     */
    public Optional<Stock> getStockById(Integer idStock) {
        return stockRepository.findById(idStock);
    }

    /**
     * Obtenir le stock d'un produit dans une boutique
     */
    public Optional<Stock> getStockByProduitAndBoutique(Integer idProduit, Integer idBoutique) {
        return stockRepository.findByIdProduitAndIdBoutique(idProduit, idBoutique);
    }

    /**
     * Obtenir tous les stocks d'une boutique
     */
    public List<Stock> getStocksByBoutique(Integer idBoutique) {
        return stockRepository.findByIdBoutique(idBoutique);
    }

    /**
     * Obtenir tous les stocks d'un produit
     */
    public List<Stock> getStocksByProduit(Integer idProduit) {
        return stockRepository.findByIdProduit(idProduit);
    }

    /**
     * Mettre à jour un stock
     */
    public Stock updateStock(Stock stock) {
        stock.setDerniereMaj(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    /**
     * Supprimer un stock
     */
    public void deleteStock(Integer idStock) {
        stockRepository.deleteById(idStock);
    }

    // === GESTION DES QUANTITÉS ===

    /**
     * Ajouter de la quantité au stock
     */
    public Stock ajouterQuantite(Integer idStock, Integer quantite, BigDecimal prixUnitaire, String notes) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            stock.setQuantiteDisponible(stock.getQuantiteDisponible() + quantite);
            stock.setDerniereMaj(LocalDateTime.now());

            // Créer une transaction d'entrée
            StockTransaction transaction = new StockTransaction(
                    stock.getIdProduit(),
                    stock.getIdStock(),
                    "ENTREE",
                    quantite,
                    prixUnitaire,
                    notes);
            stockTransactionRepository.save(transaction);

            return stockRepository.save(stock);
        }
        throw new RuntimeException("Stock non trouvé avec l'ID: " + idStock);
    }

    /**
     * Retirer de la quantité du stock
     */
    public Stock retirerQuantite(Integer idStock, Integer quantite, BigDecimal prixUnitaire, String notes) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            if (stock.getQuantiteDisponible() >= quantite) {
                stock.setQuantiteDisponible(stock.getQuantiteDisponible() - quantite);
                stock.setDerniereMaj(LocalDateTime.now());

                // Créer une transaction de sortie
                StockTransaction transaction = new StockTransaction(
                        stock.getIdProduit(),
                        stock.getIdStock(),
                        "SORTIE",
                        quantite,
                        prixUnitaire,
                        notes);
                stockTransactionRepository.save(transaction);

                return stockRepository.save(stock);
            } else {
                throw new RuntimeException("Quantité insuffisante en stock. Disponible: "
                        + stock.getQuantiteDisponible() + ", Demandé: " + quantite);
            }
        }
        throw new RuntimeException("Stock non trouvé avec l'ID: " + idStock);
    }

    /**
     * Ajuster la quantité du stock (pour corrections)
     */
    public Stock ajusterQuantite(Integer idStock, Integer nouvelleQuantite, String notes) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            int difference = nouvelleQuantite - stock.getQuantiteDisponible();
            stock.setQuantiteDisponible(nouvelleQuantite);
            stock.setDerniereMaj(LocalDateTime.now());

            // Créer une transaction d'ajustement
            StockTransaction transaction = new StockTransaction(
                    stock.getIdProduit(),
                    stock.getIdStock(),
                    "AJUSTEMENT",
                    Math.abs(difference),
                    BigDecimal.ZERO,
                    notes + " (Ancienne quantité: " + (stock.getQuantiteDisponible() - difference) + ")");
            stockTransactionRepository.save(transaction);

            return stockRepository.save(stock);
        }
        throw new RuntimeException("Stock non trouvé avec l'ID: " + idStock);
    }

    // === ALERTES ET SURVEILLANCE ===

    /**
     * Obtenir les stocks en rupture (quantité <= seuil critique)
     */
    public List<Stock> getStocksEnRupture() {
        return stockRepository.findStocksEnRupture();
    }

    /**
     * Obtenir les stocks en rupture d'une boutique
     */
    public List<Stock> getStocksEnRuptureByBoutique(Integer idBoutique) {
        return stockRepository.findStocksEnRuptureByBoutique(idBoutique);
    }

    /**
     * Vérifier si un stock est en rupture
     */
    public boolean isStockEnRupture(Integer idStock) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        return stockOpt.map(stock -> stock.getQuantiteDisponible() <= stock.getSeuilCritique()).orElse(false);
    }

    /**
     * Vérifier si un stock est épuisé
     */
    public boolean isStockEpuise(Integer idStock) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        return stockOpt.map(stock -> stock.getQuantiteDisponible() <= 0).orElse(false);
    }

    // === STATISTIQUES ===

    /**
     * Compter le nombre de produits en stock pour une boutique
     */
    public Long countProduitsEnStockByBoutique(Integer idBoutique) {
        return stockRepository.countProduitsEnStockByBoutique(idBoutique);
    }

    /**
     * Calculer la valeur totale du stock d'une boutique
     */
    public Long getValeurTotaleStockByBoutique(Integer idBoutique) {
        return stockRepository.getValeurTotaleStockByBoutique(idBoutique);
    }

    /**
     * Obtenir les stocks par statut
     */
    public List<Stock> getStocksByStatut(String statut) {
        return stockRepository.findByStatut(statut);
    }

    /**
     * Obtenir les stocks d'une boutique par statut
     */
    public List<Stock> getStocksByBoutiqueAndStatut(Integer idBoutique, String statut) {
        return stockRepository.findByIdBoutiqueAndStatut(idBoutique, statut);
    }
}