package com.example.ecommerce.stockservice.controller;

import com.example.ecommerce.stockservice.entity.StockTransaction;
import com.example.ecommerce.stockservice.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock-transactions")
@CrossOrigin(origins = "*")
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @PostMapping
    public ResponseEntity<StockTransaction> createTransaction(@RequestBody StockTransaction transaction) {
        try {
            if (transaction.getQuantity() == null || transaction.getQuantity() <= 0 ||
                    transaction.getPrixUnitaire() == null || transaction.getPrixUnitaire().compareTo(BigDecimal.ZERO) < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            StockTransaction createdTransaction = stockTransactionService.createTransaction(transaction);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockTransaction> getTransactionById(@PathVariable Integer id) {
        Optional<StockTransaction> transaction = stockTransactionService.getTransactionById(id);
        return transaction.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/produit/{idProduit}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByProduit(@PathVariable Integer idProduit) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduit(idProduit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/stock/{idStock}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByStock(@PathVariable Integer idStock) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByStock(idStock);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByBoutique(@PathVariable Integer idBoutique) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByBoutique(idBoutique);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByType(@PathVariable String type) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockTransaction> updateTransaction(@PathVariable Integer id, @RequestBody StockTransaction transaction) {
        try {
            transaction.setIdTransaction(id);
            if (transaction.getQuantity() == null || transaction.getQuantity() <= 0 ||
                    transaction.getPrixUnitaire() == null || transaction.getPrixUnitaire().compareTo(BigDecimal.ZERO) < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            StockTransaction updatedTransaction = stockTransactionService.updateTransaction(transaction);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        try {
            stockTransactionService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/produit/{idProduit}/type/{type}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByProduitAndType(@PathVariable Integer idProduit, @PathVariable String type) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduitAndType(idProduit, type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/stock/{idStock}/type/{type}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByStockAndType(@PathVariable Integer idStock, @PathVariable String type) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByStockAndType(idStock, type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/periode")
    public ResponseEntity<List<StockTransaction>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        if (dateDebut.isAfter(dateFin)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByDateRange(dateDebut, dateFin);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/periode")
    public ResponseEntity<List<StockTransaction>> getTransactionsByProduitAndDateRange(
            @PathVariable Integer idProduit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        if (dateDebut.isAfter(dateFin)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduitAndDateRange(idProduit, dateDebut, dateFin);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/revenu")
    public ResponseEntity<BigDecimal> getRevenuTotalByProduit(@PathVariable Integer idProduit) {
        BigDecimal revenu = stockTransactionService.getRevenuTotalByProduit(idProduit);
        return new ResponseEntity<>(revenu != null ? revenu : BigDecimal.ZERO, HttpStatus.OK);
    }

    @GetMapping("/boutique/{idBoutique}/revenu")
    public ResponseEntity<BigDecimal> getRevenuTotalByBoutique(@PathVariable Integer idBoutique) {
        BigDecimal revenu = stockTransactionService.getRevenuTotalByBoutique(idBoutique);
        return new ResponseEntity<>(revenu != null ? revenu : BigDecimal.ZERO, HttpStatus.OK);
    }

    @GetMapping("/revenu/periode")
    public ResponseEntity<BigDecimal> getRevenuTotalByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        if (dateDebut.isAfter(dateFin)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BigDecimal revenu = stockTransactionService.getRevenuTotalByDateRange(dateDebut, dateFin);
        return new ResponseEntity<>(revenu != null ? revenu : BigDecimal.ZERO, HttpStatus.OK);
    }

    @GetMapping("/revenu/min/{montant}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByRevenuMin(@PathVariable BigDecimal montant) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByRevenuMin(montant);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/count/par-type")
    public ResponseEntity<List<Object[]>> countTransactionsByType() {
        List<Object[]> counts = stockTransactionService.countTransactionsByType();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/entrees")
    public ResponseEntity<List<StockTransaction>> getTransactionsEntreeByProduit(@PathVariable Integer idProduit) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsEntreeByProduit(idProduit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/sorties")
    public ResponseEntity<List<StockTransaction>> getTransactionsSortieByProduit(@PathVariable Integer idProduit) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsSortieByProduit(idProduit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/ajustements")
    public ResponseEntity<List<StockTransaction>> getTransactionsAjustementByProduit(@PathVariable Integer idProduit) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsAjustementByProduit(idProduit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/retours")
    public ResponseEntity<List<StockTransaction>> getTransactionsRetourByProduit(@PathVariable Integer idProduit) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsRetourByProduit(idProduit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/quantite-entree")
    public ResponseEntity<Integer> getQuantiteTotaleEntreeByProduit(@PathVariable Integer idProduit) {
        Integer quantite = stockTransactionService.getQuantiteTotaleEntreeByProduit(idProduit);
        return new ResponseEntity<>(quantite != null ? quantite : 0, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/quantite-sortie")
    public ResponseEntity<Integer> getQuantiteTotaleSortieByProduit(@PathVariable Integer idProduit) {
        Integer quantite = stockTransactionService.getQuantiteTotaleSortieByProduit(idProduit);
        return new ResponseEntity<>(quantite != null ? quantite : 0, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/revenu-entrees")
    public ResponseEntity<BigDecimal> getRevenuTotalEntreesByProduit(@PathVariable Integer idProduit) {
        BigDecimal revenu = stockTransactionService.getRevenuTotalEntreesByProduit(idProduit);
        return new ResponseEntity<>(revenu != null ? revenu : BigDecimal.ZERO, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}/revenu-sorties")
    public ResponseEntity<BigDecimal> getRevenuTotalSortiesByProduit(@PathVariable Integer idProduit) {
        BigDecimal revenu = stockTransactionService.getRevenuTotalSortiesByProduit(idProduit);
        return new ResponseEntity<>(revenu != null ? revenu : BigDecimal.ZERO, HttpStatus.OK);
    }
}