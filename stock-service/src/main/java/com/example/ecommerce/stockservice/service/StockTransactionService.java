package com.example.ecommerce.stockservice.service;

import com.example.ecommerce.stockservice.entity.Stock;
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

    @Autowired
    private StockService stockService;

    public StockTransaction createTransaction(StockTransaction transaction) {
        if (transaction.getStock() == null || transaction.getStock().getIdStock() == null) {
            throw new IllegalArgumentException("Un stock valide est requis.");
        }
        Optional<Stock> existingStock = stockService.getStockById(transaction.getStock().getIdStock());
        if (existingStock.isEmpty()) {
            throw new IllegalArgumentException("Le stock spécifié n'existe pas.");
        }
        if ("SORTIE".equalsIgnoreCase(transaction.getType()) && existingStock.get().getQuantiteDisponible() < transaction.getQuantity()) {
            throw new IllegalArgumentException("Quantité insuffisante en stock. Disponible: "
                    + existingStock.get().getQuantiteDisponible() + ", Demandé: " + transaction.getQuantity());
        }
        if (transaction.getIdProduit() == null || transaction.getQuantity() == null || transaction.getQuantity() <= 0 ||
                transaction.getPrixUnitaire() == null || transaction.getPrixUnitaire().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les champs idProduit, quantity, et prixUnitaire sont requis et doivent être valides.");
        }
        transaction.setStock(existingStock.get());
        transaction.setTransactionDate(LocalDateTime.now());
        if (transaction.getRevenuTotal() == null) {
            transaction.setRevenuTotal(transaction.getPrixUnitaire().multiply(BigDecimal.valueOf(transaction.getQuantity())));
        }
        if ("SORTIE".equalsIgnoreCase(transaction.getType())) {
            Stock stock = existingStock.get();
            stock.setQuantiteDisponible(stock.getQuantiteDisponible() - transaction.getQuantity());
            stockService.updateStock(stock);
        }
        return stockTransactionRepository.save(transaction);
    }

    public Optional<StockTransaction> getTransactionById(Integer id) {
        return stockTransactionRepository.findById(id);
    }

    public List<StockTransaction> getTransactionsByProduit(Integer idProduit) {
        return stockTransactionRepository.findByIdProduitOrderByTransactionDateDesc(idProduit);
    }

    public List<StockTransaction> getTransactionsByStock(Integer idStock) {
        return stockTransactionRepository.findByStock_IdStockOrderByTransactionDateDesc(idStock);
    }

    public List<StockTransaction> getTransactionsByBoutique(Integer idBoutique) {
        return stockTransactionRepository.findByStock_IdBoutique(idBoutique);
    }

    public List<StockTransaction> getTransactionsByType(String type) {
        return stockTransactionRepository.findByTypeOrderByTransactionDateDesc(type);
    }

    public StockTransaction updateTransaction(StockTransaction transaction) {
        if (transaction.getIdTransaction() == null) {
            throw new IllegalArgumentException("L'ID de la transaction est requis.");
        }
        if (transaction.getQuantity() == null || transaction.getQuantity() <= 0 ||
                transaction.getPrixUnitaire() == null || transaction.getPrixUnitaire().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les champs quantity et prixUnitaire doivent être valides.");
        }
        transaction.setRevenuTotal(transaction.getPrixUnitaire().multiply(BigDecimal.valueOf(transaction.getQuantity())));
        return stockTransactionRepository.save(transaction);
    }

    public void deleteTransaction(Integer id) {
        stockTransactionRepository.deleteById(id);
    }

    public List<StockTransaction> getTransactionsByProduitAndType(Integer idProduit, String type) {
        return stockTransactionRepository.findByIdProduitAndTypeOrderByTransactionDateDesc(idProduit, type);
    }

    public List<StockTransaction> getTransactionsByStockAndType(Integer idStock, String type) {
        return stockTransactionRepository.findByStock_IdStockAndTypeOrderByTransactionDateDesc(idStock, type);
    }

    public List<StockTransaction> getTransactionsByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return stockTransactionRepository.findTransactionsByDateRange(dateDebut, dateFin);
    }

    public List<StockTransaction> getTransactionsByProduitAndDateRange(Integer idProduit, LocalDateTime dateDebut, LocalDateTime dateFin) {
        return stockTransactionRepository.findTransactionsByProduitAndDateRange(idProduit, dateDebut, dateFin);
    }

    public BigDecimal getRevenuTotalByProduit(Integer idProduit) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByProduit(idProduit);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    public BigDecimal getRevenuTotalByBoutique(Integer idBoutique) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByBoutique(idBoutique);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    public BigDecimal getRevenuTotalByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        BigDecimal revenu = stockTransactionRepository.getRevenuTotalByDateRange(dateDebut, dateFin);
        return revenu != null ? revenu : BigDecimal.ZERO;
    }

    public List<StockTransaction> getTransactionsByRevenuMin(BigDecimal montant) {
        return stockTransactionRepository.findTransactionsByRevenuMin(montant);
    }

    public List<Object[]> countTransactionsByType() {
        return stockTransactionRepository.countTransactionsByType();
    }

    public List<StockTransaction> getTransactionsEntreeByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "ENTREE");
    }

    public List<StockTransaction> getTransactionsSortieByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "SORTIE");
    }

    public List<StockTransaction> getTransactionsAjustementByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "AJUSTEMENT");
    }

    public List<StockTransaction> getTransactionsRetourByProduit(Integer idProduit) {
        return getTransactionsByProduitAndType(idProduit, "RETOUR");
    }

    public Integer getQuantiteTotaleEntreeByProduit(Integer idProduit) {
        List<StockTransaction> transactions = getTransactionsEntreeByProduit(idProduit);
        return transactions.stream().mapToInt(StockTransaction::getQuantity).sum();
    }

    public Integer getQuantiteTotaleSortieByProduit(Integer idProduit) {
        List<StockTransaction> transactions = getTransactionsSortieByProduit(idProduit);
        return transactions.stream().mapToInt(StockTransaction::getQuantity).sum();
    }

    public BigDecimal getRevenuTotalEntreesByProduit(Integer idProduit) {
        List<StockTransaction> transactions = getTransactionsEntreeByProduit(idProduit);
        return transactions.stream().map(StockTransaction::getRevenuTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRevenuTotalSortiesByProduit(Integer idProduit) {
        List<StockTransaction> transactions = getTransactionsSortieByProduit(idProduit);
        return transactions.stream().map(StockTransaction::getRevenuTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}