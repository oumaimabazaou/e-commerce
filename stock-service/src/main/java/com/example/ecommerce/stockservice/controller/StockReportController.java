package com.example.ecommerce.stockservice.controller;

import com.example.ecommerce.stockservice.entity.Stock;
import com.example.ecommerce.stockservice.entity.StockTransaction;
import com.example.ecommerce.stockservice.service.StockService;
import com.example.ecommerce.stockservice.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-reports")
@CrossOrigin(origins = "*")
public class StockReportController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockTransactionService stockTransactionService;

    // === RAPPORTS DE STOCK ===

    /**
     * Rapport complet d'un stock
     */
    @GetMapping("/stock/{idStock}")
    public ResponseEntity<Map<String, Object>> getStockReport(@PathVariable Integer idStock) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Informations du stock
            stockService.getStockById(idStock).ifPresent(stock -> {
                report.put("stock", stock);
                report.put("enRupture", stockService.isStockEnRupture(idStock));
                report.put("epuise", stockService.isStockEpuise(idStock));

                // Statistiques des transactions
                List<StockTransaction> transactions = stockTransactionService.getTransactionsByStock(idStock);
                report.put("nombreTransactions", transactions.size());

                // Revenus
                BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByProduit(stock.getIdProduit());
                report.put("revenuTotal", revenuTotal);

                // Quantités
                Integer quantiteEntree = stockTransactionService.getQuantiteTotaleEntreeByProduit(stock.getIdProduit());
                Integer quantiteSortie = stockTransactionService.getQuantiteTotaleSortieByProduit(stock.getIdProduit());
                report.put("quantiteTotaleEntree", quantiteEntree);
                report.put("quantiteTotaleSortie", quantiteSortie);
                report.put("quantiteDisponible", stock.getQuantiteDisponible());

                // Revenus par type
                BigDecimal revenuEntrees = stockTransactionService.getRevenuTotalEntreesByProduit(stock.getIdProduit());
                BigDecimal revenuSorties = stockTransactionService.getRevenuTotalSortiesByProduit(stock.getIdProduit());
                report.put("revenuEntrees", revenuEntrees);
                report.put("revenuSorties", revenuSorties);
            });

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rapport complet d'une boutique
     */
    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<Map<String, Object>> getBoutiqueReport(@PathVariable Integer idBoutique) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Stocks de la boutique
            List<Stock> stocks = stockService.getStocksByBoutique(idBoutique);
            report.put("stocks", stocks);
            report.put("nombreStocks", stocks.size());

            // Stocks en rupture
            List<Stock> stocksEnRupture = stockService.getStocksEnRuptureByBoutique(idBoutique);
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            // Statistiques
            Long nombreProduitsEnStock = stockService.countProduitsEnStockByBoutique(idBoutique);
            Long valeurTotaleStock = stockService.getValeurTotaleStockByBoutique(idBoutique);
            report.put("nombreProduitsEnStock", nombreProduitsEnStock);
            report.put("valeurTotaleStock", valeurTotaleStock);

            // Transactions de la boutique
            List<StockTransaction> transactions = stockTransactionService.getTransactionsByBoutique(idBoutique);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            // Revenus
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByBoutique(idBoutique);
            report.put("revenuTotal", revenuTotal);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rapport d'un produit
     */
    @GetMapping("/produit/{idProduit}")
    public ResponseEntity<Map<String, Object>> getProduitReport(@PathVariable Integer idProduit) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Stocks du produit
            List<Stock> stocks = stockService.getStocksByProduit(idProduit);
            report.put("stocks", stocks);
            report.put("nombreStocks", stocks.size());

            // Transactions du produit
            List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduit(idProduit);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            // Revenus
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByProduit(idProduit);
            report.put("revenuTotal", revenuTotal);

            // Quantités
            Integer quantiteEntree = stockTransactionService.getQuantiteTotaleEntreeByProduit(idProduit);
            Integer quantiteSortie = stockTransactionService.getQuantiteTotaleSortieByProduit(idProduit);
            report.put("quantiteTotaleEntree", quantiteEntree);
            report.put("quantiteTotaleSortie", quantiteSortie);

            // Revenus par type
            BigDecimal revenuEntrees = stockTransactionService.getRevenuTotalEntreesByProduit(idProduit);
            BigDecimal revenuSorties = stockTransactionService.getRevenuTotalSortiesByProduit(idProduit);
            report.put("revenuEntrees", revenuEntrees);
            report.put("revenuSorties", revenuSorties);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // === RAPPORTS TEMPORELS ===

    /**
     * Rapport de stock pour une période donnée
     */
    @GetMapping("/periode")
    public ResponseEntity<Map<String, Object>> getPeriodReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Transactions de la période
            List<StockTransaction> transactions = stockTransactionService.getTransactionsByDateRange(dateDebut,
                    dateFin);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            // Revenus de la période
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByDateRange(dateDebut, dateFin);
            report.put("revenuTotal", revenuTotal);

            // Statistiques par type
            List<Object[]> countByType = stockTransactionService.countTransactionsByType();
            report.put("statistiquesParType", countByType);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rapport d'un produit pour une période donnée
     */
    @GetMapping("/produit/{idProduit}/periode")
    public ResponseEntity<Map<String, Object>> getProduitPeriodReport(
            @PathVariable Integer idProduit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Transactions du produit pour la période
            List<StockTransaction> transactions = stockTransactionService
                    .getTransactionsByProduitAndDateRange(idProduit, dateDebut, dateFin);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            // Revenus du produit pour la période
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByProduit(idProduit);
            report.put("revenuTotal", revenuTotal);

            // Quantités par type pour la période
            List<StockTransaction> entrees = stockTransactionService.getTransactionsEntreeByProduit(idProduit);
            List<StockTransaction> sorties = stockTransactionService.getTransactionsSortieByProduit(idProduit);
            report.put("entrees", entrees);
            report.put("sorties", sorties);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // === ALERTES ET NOTIFICATIONS ===

    /**
     * Rapport des alertes de stock
     */
    @GetMapping("/alertes")
    public ResponseEntity<Map<String, Object>> getAlertesReport() {
        Map<String, Object> report = new HashMap<>();

        try {
            // Stocks en rupture
            List<Stock> stocksEnRupture = stockService.getStocksEnRupture();
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            // Stocks épuisés
            List<Stock> stocksEpuises = stockService.getStocksByStatut("EPUISE");
            report.put("stocksEpuises", stocksEpuises);
            report.put("nombreStocksEpuises", stocksEpuises.size());

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rapport des alertes d'une boutique
     */
    @GetMapping("/alertes/boutique/{idBoutique}")
    public ResponseEntity<Map<String, Object>> getBoutiqueAlertesReport(@PathVariable Integer idBoutique) {
        Map<String, Object> report = new HashMap<>();

        try {
            // Stocks en rupture de la boutique
            List<Stock> stocksEnRupture = stockService.getStocksEnRuptureByBoutique(idBoutique);
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            // Stocks épuisés de la boutique
            List<Stock> stocksEpuises = stockService.getStocksByBoutiqueAndStatut(idBoutique, "EPUISE");
            report.put("stocksEpuises", stocksEpuises);
            report.put("nombreStocksEpuises", stocksEpuises.size());

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // === STATISTIQUES GLOBALES ===

    /**
     * Statistiques globales du système de stock
     */
    @GetMapping("/statistiques-globales")
    public ResponseEntity<Map<String, Object>> getGlobalStatistics() {
        Map<String, Object> report = new HashMap<>();

        try {
            // Compter les transactions par type
            List<Object[]> countByType = stockTransactionService.countTransactionsByType();
            report.put("transactionsParType", countByType);

            // Revenus totaux
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByDateRange(
                    LocalDateTime.now().minusDays(30), LocalDateTime.now());
            report.put("revenuTotal30Jours", revenuTotal);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}