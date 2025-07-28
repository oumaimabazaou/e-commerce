package com.example.ecommerce.stockservice.controller;

import com.example.ecommerce.stockservice.entity.Stock;
import com.example.ecommerce.stockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    // === CRUD BASIQUE ===

    /**
     * Créer un nouveau stock
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        try {
            Stock createdStock = stockService.createStock(stock);
            return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtenir un stock par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtenir le stock d'un produit dans une boutique
     */
    @GetMapping("/produit/{idProduit}/boutique/{idBoutique}")
    public ResponseEntity<Stock> getStockByProduitAndBoutique(@PathVariable Integer idProduit, 
                                                             @PathVariable Integer idBoutique) {
        Optional<Stock> stock = stockService.getStockByProduitAndBoutique(idProduit, idBoutique);
        return stock.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtenir tous les stocks d'une boutique
     */
    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Stock>> getStocksByBoutique(@PathVariable Integer idBoutique) {
        List<Stock> stocks = stockService.getStocksByBoutique(idBoutique);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Obtenir tous les stocks d'un produit
     */
    @GetMapping("/produit/{idProduit}")
    public ResponseEntity<List<Stock>> getStocksByProduit(@PathVariable Integer idProduit) {
        List<Stock> stocks = stockService.getStocksByProduit(idProduit);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Mettre à jour un stock
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stock) {
        try {
            stock.setIdStock(id);
            Stock updatedStock = stockService.updateStock(stock);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Supprimer un stock
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        try {
            stockService.deleteStock(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // === GESTION DES QUANTITÉS ===

    /**
     * Ajouter de la quantité au stock
     */
    @PostMapping("/{id}/ajouter")
    public ResponseEntity<Stock> ajouterQuantite(@PathVariable Integer id,
                                                @RequestParam Integer quantite,
                                                @RequestParam BigDecimal prixUnitaire,
                                                @RequestParam(required = false) String notes) {
        try {
            Stock updatedStock = stockService.ajouterQuantite(id, quantite, prixUnitaire, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retirer de la quantité du stock
     */
    @PostMapping("/{id}/retirer")
    public ResponseEntity<Stock> retirerQuantite(@PathVariable Integer id,
                                                @RequestParam Integer quantite,
                                                @RequestParam BigDecimal prixUnitaire,
                                                @RequestParam(required = false) String notes) {
        try {
            Stock updatedStock = stockService.retirerQuantite(id, quantite, prixUnitaire, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Ajuster la quantité du stock
     */
    @PostMapping("/{id}/ajuster")
    public ResponseEntity<Stock> ajusterQuantite(@PathVariable Integer id,
                                                @RequestParam Integer nouvelleQuantite,
                                                @RequestParam(required = false) String notes) {
        try {
            Stock updatedStock = stockService.ajusterQuantite(id, nouvelleQuantite, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // === ALERTES ET SURVEILLANCE ===

    /**
     * Obtenir les stocks en rupture
     */
    @GetMapping("/rupture")
    public ResponseEntity<List<Stock>> getStocksEnRupture() {
        List<Stock> stocks = stockService.getStocksEnRupture();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Obtenir les stocks en rupture d'une boutique
     */
    @GetMapping("/rupture/boutique/{idBoutique}")
    public ResponseEntity<List<Stock>> getStocksEnRuptureByBoutique(@PathVariable Integer idBoutique) {
        List<Stock> stocks = stockService.getStocksEnRuptureByBoutique(idBoutique);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Vérifier si un stock est en rupture
     */
    @GetMapping("/{id}/rupture")
    public ResponseEntity<Boolean> isStockEnRupture(@PathVariable Integer id) {
        boolean isEnRupture = stockService.isStockEnRupture(id);
        return new ResponseEntity<>(isEnRupture, HttpStatus.OK);
    }

    /**
     * Vérifier si un stock est épuisé
     */
    @GetMapping("/{id}/epuise")
    public ResponseEntity<Boolean> isStockEpuise(@PathVariable Integer id) {
        boolean isEpuise = stockService.isStockEpuise(id);
        return new ResponseEntity<>(isEpuise, HttpStatus.OK);
    }

    // === STATISTIQUES ===

    /**
     * Compter le nombre de produits en stock pour une boutique
     */
    @GetMapping("/boutique/{idBoutique}/count")
    public ResponseEntity<Long> countProduitsEnStockByBoutique(@PathVariable Integer idBoutique) {
        Long count = stockService.countProduitsEnStockByBoutique(idBoutique);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * Calculer la valeur totale du stock d'une boutique
     */
    @GetMapping("/boutique/{idBoutique}/valeur")
    public ResponseEntity<Long> getValeurTotaleStockByBoutique(@PathVariable Integer idBoutique) {
        Long valeur = stockService.getValeurTotaleStockByBoutique(idBoutique);
        return new ResponseEntity<>(valeur, HttpStatus.OK);
    }

    /**
     * Obtenir les stocks par statut
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Stock>> getStocksByStatut(@PathVariable String statut) {
        List<Stock> stocks = stockService.getStocksByStatut(statut);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Obtenir les stocks d'une boutique par statut
     */
    @GetMapping("/boutique/{idBoutique}/statut/{statut}")
    public ResponseEntity<List<Stock>> getStocksByBoutiqueAndStatut(@PathVariable Integer idBoutique,
                                                                   @PathVariable String statut) {
        List<Stock> stocks = stockService.getStocksByBoutiqueAndStatut(idBoutique, statut);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }
} 