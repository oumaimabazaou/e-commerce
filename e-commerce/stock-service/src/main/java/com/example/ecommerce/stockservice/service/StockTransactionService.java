package com.example.ecommerce.stockservice.service;

import com.example.ecommerce.stockservice.entity.StockTransaction;
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
public class StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    // === GESTION DES TRANSACTIONS ===

    /**
     * Créer une nouvelle transaction
     */
    public StockTransaction createTransaction(StockTransaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now());
        if (transaction.getRevenuTotal() == null) {
            transaction.setRevenuTotal(
                    transaction.getPrixUnitaire().multiply(BigDecimal.valueOf(transaction.getQuantity())));
        }
        return stockTransactionRepository.save(transaction);
    }

    /**
     * Obtenir une transaction par son ID
     */
    public Optional<StockTransaction> getTransactionById(Integer idTransaction) {
        return stockTransactionRepository.findById(idTransaction);
    }

    /**
     * Obtenir toutes les transactions d'un produit
     */
    public List<StockTransaction> getTransactionsByProduit(Integer idProduit) {
        return stockTransactionRepository.findByIdProduitOrderByTransactionDateDesc(idProduit);
    }

    /**
     * Obtenir toutes les transactions d'un stock
     */
    public List<StockTransaction> getTransactionsByStock(Integer idStock) {
        return stockTransactionRepository.findByIdStockOrderByTransactionDateDesc(idStock);
    }

    /**
     * Obtenir toutes les transactions d'une boutique
     */
    public List<StockTransaction> getTransactionsByBoutique(Integer idBoutique) {
        return stockTransactionRepository.findTransactionsByBoutique(idBoutique);
    }

    /**
     * Obtenir les transactions par type
     */
    public List<StockTransaction> getTransactionsByType(String type) {
        return stockTransactionRepository.findByTypeOrderByTransactionDateDesc(type);
    }

    /**
     * Obtenir les transactions d'un produit par type
     */
    public List<StockTransaction> getTransactionsByProduitAndType(Integer idProduit, String type) {
        return stockTransactionRepository.findByIdProduitAndTypeOrderByTransactionDateDesc(idProduit, type);
    }

    /**
     * Obtenir les transactions d'un stock par type
     */
    public List<StockTransaction> getTransactionsByStockAndType(Integer idStock, String type) {
        return stockTransactionRepository.findByIdStockAndTypeOrderByTransactionDateDesc(idStock, type);
    }

    /**
     * Obtenir les transactions dans une période donnée
     */
    public List<StockTransaction> getTransactionsByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return stockTransactionRepository.findTransactionsByDateRange(dateDebut, dateFin);
    }

    /**
     * Obtenir les transactions d'un produit dans une période donnée
     */
    public List<StockTransaction> getTransactionsByProduitAndDateRange(Integer idProduit, LocalDateTime dateDebut,
            LocalDateTime dateFin) {
        return stockTransactionRepository.findTransactionsByProduitAndDateRange(idProduit, dateDebut, dateFin);
    }

    /**
     * Mettre à jour une transaction
     */
    public StockTransaction updateTransaction(StockTransaction transaction) {
        if (transaction.getRevenuTotal() == null) {
            transaction.setRevenuTotal(
                    transaction.getPrixUnitaire().multiply(BigDecimal.valueOf(transaction.getQuantity())));
        }
        return stockTransactionRepository.save(transaction);
    }

    /**
     * Supprimer une transaction
     */
    public void deleteTransaction(Integer idTransaction) {
        stockTransactionRepository.deleteById(idTransaction);
    }

    // === ANALYTICS ET RAPPORTS ===

    /**
     * Calculer le revenu total des transactions d'un produit
     */
    public BigDecimal getRevenuTotalByProduit(Integer idProduit) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByProduit(idProduit);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    /**
     * Calculer le revenu total des transactions d'une boutique
     */
    public BigDecimal getRevenuTotalByBoutique(Integer idBoutique) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByBoutique(idBoutique);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    /**
     * Calculer le revenu total des transactions dans une période donnée
     */
    public BigDecimal getRevenuTotalByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByDateRange(dateDebut, dateFin);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    /**
     * Obtenir les transactions avec revenu supérieur à un montant
     */
    public List<StockTransaction> getTransactionsByRevenuMin(BigDecimal montant) {
        return stockTransactionRepository.findTransactionsByRevenuMin(montant);
    }

    /**
     * Compter le nombre de transactions par type
     */
    public List<Object[]> countTransactionsByType() {
        return stockTransactionRepository.countTransactionsByType();
    }

    // === MÉTHODES SPÉCIALISÉES ===

    /**
     * Obtenir les transactions d'entrée d'un produit
     */
    public List<StockTransaction> getTransactionsEntreeByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "ENTREE");
    }

    /**
     * Obtenir les transactions de sortie d'un produit
     */
    public List<StockTransaction> getTransactionsSortieByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "SORTIE");
    }

    /**
     * Obtenir les transactions d'ajustement d'un produit
     */
    public List<StockTransaction> getTransactionsAjustementByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "AJUSTEMENT");
    }

    /**
     * Obtenir les transactions de retour d'un produit
     */
    public List<StockTransaction> getTransactionsRetourByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "RETOUR");
    }

    /**
     * Calculer la quantité totale entrée pour un produit
     */
    public Integer getQuantiteTotaleEntreeByProduit(Integer idProduit) {
        return getTransactionsEntreeByProduit(idProduit)
                .stream()
                .mapToInt(StockTransaction::getQuantity)
                .sum();
    }

    /**
     * Calculer la quantité totale sortie pour un produit
     */
    public Integer getQuantiteTotaleSortieByProduit(Integer idProduit) {
        return getTransactionsSortieByProduit(idProduit)
                .stream()
                .mapToInt(StockTransaction::getQuantity)
                .sum();
    }

    /**
     * Calculer le revenu total des entrées pour un produit
     */
    public BigDecimal getRevenuTotalEntreesByProduit(Integer idProduit) {
        return getTransactionsEntreeByProduit(idProduit)
                .stream()
                .map(StockTransaction::getRevenuTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculer le revenu total des sorties pour un produit
     */
    public BigDecimal getRevenuTotalSortiesByProduit(Integer idProduit) {
        return getTransactionsSortieByProduit(idProduit)
                .stream()
                .map(StockTransaction::getRevenuTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}