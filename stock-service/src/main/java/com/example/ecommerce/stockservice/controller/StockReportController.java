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

    @GetMapping("/stock/{idStock}")
    public ResponseEntity<Map<String, Object>> getStockReport(@PathVariable Integer idStock) {
        Map<String, Object> report = new HashMap<>();
        try {
            stockService.getStockById(idStock).ifPresentOrElse(stock -> {
                report.put("stock", stock);
                report.put("enRupture", stockService.isStockEnRupture(idStock));
                report.put("epuise", stockService.isStockEpuise(idStock));

                List<StockTransaction> transactions = stockTransactionService.getTransactionsByStock(idStock);
                report.put("nombreTransactions", transactions.size());

                BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByProduit(stock.getIdProduit());
                report.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

                Integer quantiteEntree = stockTransactionService.getQuantiteTotaleEntreeByProduit(stock.getIdProduit());
                Integer quantiteSortie = stockTransactionService.getQuantiteTotaleSortieByProduit(stock.getIdProduit());
                report.put("quantiteTotaleEntree", quantiteEntree != null ? quantiteEntree : 0);
                report.put("quantiteTotaleSortie", quantiteSortie != null ? quantiteSortie : 0);
                report.put("quantiteDisponible", stock.getQuantiteDisponible());

                BigDecimal revenuEntrees = stockTransactionService.getRevenuTotalEntreesByProduit(stock.getIdProduit());
                BigDecimal revenuSorties = stockTransactionService.getRevenuTotalSortiesByProduit(stock.getIdProduit());
                report.put("revenuEntrees", revenuEntrees != null ? revenuEntrees : BigDecimal.ZERO);
                report.put("revenuSorties", revenuSorties != null ? revenuSorties : BigDecimal.ZERO);
            }, () -> report.put("message", "Aucun stock trouvé pour l'ID " + idStock));

            return report.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<Map<String, Object>> getBoutiqueReport(@PathVariable Integer idBoutique) {
        Map<String, Object> report = new HashMap<>();
        try {
            List<Stock> stocks = stockService.getStocksByBoutique(idBoutique);
            if (stocks.isEmpty()) {
                report.put("message", "Aucune donnée de stock pour la boutique " + idBoutique);
                return new ResponseEntity<>(report, HttpStatus.NOT_FOUND);
            }
            report.put("stocks", stocks);
            report.put("nombreStocks", stocks.size());

            List<Stock> stocksEnRupture = stockService.getStocksEnRuptureByBoutique(idBoutique);
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            Long nombreProduitsEnStock = stockService.countProduitsEnStockByBoutique(idBoutique);
            Long valeurTotaleStock = stockService.getValeurTotaleStockByBoutique(idBoutique);
            report.put("nombreProduitsEnStock", nombreProduitsEnStock != null ? nombreProduitsEnStock : 0L);
            report.put("valeurTotaleStock", valeurTotaleStock != null ? valeurTotaleStock : 0L);

            List<StockTransaction> transactions = stockTransactionService.getTransactionsByBoutique(idBoutique);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByBoutique(idBoutique);
            report.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/produit/{idProduit}")
    public ResponseEntity<Map<String, Object>> getProduitReport(@PathVariable Integer idProduit) {
        Map<String, Object> report = new HashMap<>();
        try {
            List<Stock> stocks = stockService.getStocksByProduit(idProduit);
            if (stocks.isEmpty()) {
                report.put("message", "Aucune donnée de stock pour le produit " + idProduit);
                return new ResponseEntity<>(report, HttpStatus.NOT_FOUND);
            }
            report.put("stocks", stocks);
            report.put("nombreStocks", stocks.size());

            List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduit(idProduit);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByProduit(idProduit);
            report.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

            Integer quantiteEntree = stockTransactionService.getQuantiteTotaleEntreeByProduit(idProduit);
            Integer quantiteSortie = stockTransactionService.getQuantiteTotaleSortieByProduit(idProduit);
            report.put("quantiteTotaleEntree", quantiteEntree != null ? quantiteEntree : 0);
            report.put("quantiteTotaleSortie", quantiteSortie != null ? quantiteSortie : 0);

            BigDecimal revenuEntrees = stockTransactionService.getRevenuTotalEntreesByProduit(idProduit);
            BigDecimal revenuSorties = stockTransactionService.getRevenuTotalSortiesByProduit(idProduit);
            report.put("revenuEntrees", revenuEntrees != null ? revenuEntrees : BigDecimal.ZERO);
            report.put("revenuSorties", revenuSorties != null ? revenuSorties : BigDecimal.ZERO);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/periode")
    public ResponseEntity<Map<String, Object>> getPeriodReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        Map<String, Object> report = new HashMap<>();
        try {
            if (dateDebut.isAfter(dateFin)) {
                report.put("message", "dateDebut doit être antérieure à dateFin");
                return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
            }

            List<StockTransaction> transactions = stockTransactionService.getTransactionsByDateRange(dateDebut, dateFin);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByDateRange(dateDebut, dateFin);
            report.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

            List<Object[]> countByType = stockTransactionService.countTransactionsByType();
            report.put("statistiquesParType", countByType);

            return transactions.isEmpty() ? new ResponseEntity<>(report, HttpStatus.NO_CONTENT) : new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/produit/{idProduit}/periode")
    public ResponseEntity<Map<String, Object>> getProduitPeriodReport(
            @PathVariable Integer idProduit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        Map<String, Object> report = new HashMap<>();
        try {
            if (dateDebut.isAfter(dateFin)) {
                report.put("message", "dateDebut doit être antérieure à dateFin");
                return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);
            }

            List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduitAndDateRange(idProduit, dateDebut, dateFin);
            report.put("transactions", transactions);
            report.put("nombreTransactions", transactions.size());

            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByDateRange(dateDebut, dateFin); // Ajusté pour cohérence
            report.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

            List<StockTransaction> entrees = stockTransactionService.getTransactionsEntreeByProduit(idProduit);
            List<StockTransaction> sorties = stockTransactionService.getTransactionsSortieByProduit(idProduit);
            report.put("entrees", entrees);
            report.put("sorties", sorties);

            return transactions.isEmpty() ? new ResponseEntity<>(report, HttpStatus.NO_CONTENT) : new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alertes")
    public ResponseEntity<Map<String, Object>> getAlertesReport() {
        Map<String, Object> report = new HashMap<>();
        try {
            List<Stock> stocksEnRupture = stockService.getStocksEnRupture();
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            List<Stock> stocksEpuises = stockService.getStocksByStatut("EPUISE");
            report.put("stocksEpuises", stocksEpuises);
            report.put("nombreStocksEpuises", stocksEpuises.size());

            return (stocksEnRupture.isEmpty() && stocksEpuises.isEmpty()) ?
                    new ResponseEntity<>(report, HttpStatus.NO_CONTENT) : new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alertes/boutique/{idBoutique}")
    public ResponseEntity<Map<String, Object>> getBoutiqueAlertesReport(@PathVariable Integer idBoutique) {
        Map<String, Object> report = new HashMap<>();
        try {
            List<Stock> stocksEnRupture = stockService.getStocksEnRuptureByBoutique(idBoutique);
            report.put("stocksEnRupture", stocksEnRupture);
            report.put("nombreStocksEnRupture", stocksEnRupture.size());

            List<Stock> stocksEpuises = stockService.getStocksByBoutiqueAndStatut(idBoutique, "EPUISE");
            report.put("stocksEpuises", stocksEpuises);
            report.put("nombreStocksEpuises", stocksEpuises.size());

            return (stocksEnRupture.isEmpty() && stocksEpuises.isEmpty()) ?
                    new ResponseEntity<>(report, HttpStatus.NO_CONTENT) : new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/statistiques-globales")
    public ResponseEntity<Map<String, Object>> getGlobalStatistics() {
        Map<String, Object> report = new HashMap<>();
        try {
            List<Object[]> countByType = stockTransactionService.countTransactionsByType();
            report.put("transactionsParType", countByType);

            LocalDateTime startDate = LocalDateTime.now().minusDays(30);
            LocalDateTime endDate = LocalDateTime.now();
            BigDecimal revenuTotal = stockTransactionService.getRevenuTotalByDateRange(startDate, endDate);
            report.put("revenuTotal30Jours", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);

            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}