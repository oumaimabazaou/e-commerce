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

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        try {
            if (stock.getQuantiteDisponible() == null || stock.getQuantiteDisponible() < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Stock createdStock = stockService.createStock(stock);
            return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/produit/{idProduit}/boutique/{idBoutique}")
    public ResponseEntity<Stock> getStockByProduitAndBoutique(@PathVariable Integer idProduit, @PathVariable Integer idBoutique) {
        Optional<Stock> stock = stockService.getStockByProduitAndBoutique(idProduit, idBoutique);
        return stock.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<Stock>> getStocksByBoutique(@PathVariable Integer idBoutique) {
        List<Stock> stocks = stockService.getStocksByBoutique(idBoutique);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/produit/{idProduit}")
    public ResponseEntity<List<Stock>> getStocksByProduit(@PathVariable Integer idProduit) {
        List<Stock> stocks = stockService.getStocksByProduit(idProduit);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stock) {
        try {
            stock.setIdStock(id);
            Stock updatedStock = stockService.updateStock(stock);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        try {
            stockService.deleteStock(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/ajouter")
    public ResponseEntity<Stock> ajouterQuantite(@PathVariable Integer id,
                                                 @RequestParam Integer quantite,
                                                 @RequestParam BigDecimal prixUnitaire,
                                                 @RequestParam(required = false) String notes) {
        try {
            if (quantite == null || quantite <= 0 || prixUnitaire == null || prixUnitaire.compareTo(BigDecimal.ZERO) < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Stock updatedStock = stockService.ajouterQuantite(id, quantite, prixUnitaire, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/retirer")
    public ResponseEntity<Stock> retirerQuantite(@PathVariable Integer id,
                                                 @RequestParam Integer quantite,
                                                 @RequestParam BigDecimal prixUnitaire,
                                                 @RequestParam(required = false) String notes) {
        try {
            if (quantite == null || quantite <= 0 || prixUnitaire == null || prixUnitaire.compareTo(BigDecimal.ZERO) < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Stock updatedStock = stockService.retirerQuantite(id, quantite, prixUnitaire, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/ajuster")
    public ResponseEntity<Stock> ajusterQuantite(@PathVariable Integer id,
                                                 @RequestParam Integer nouvelleQuantite,
                                                 @RequestParam(required = false) String notes) {
        try {
            if (nouvelleQuantite == null || nouvelleQuantite < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Stock updatedStock = stockService.ajusterQuantite(id, nouvelleQuantite, notes);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rupture")
    public ResponseEntity<List<Stock>> getStocksEnRupture() {
        List<Stock> stocks = stockService.getStocksEnRupture();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/rupture/boutique/{idBoutique}")
    public ResponseEntity<List<Stock>> getStocksEnRuptureByBoutique(@PathVariable Integer idBoutique) {
        List<Stock> stocks = stockService.getStocksEnRuptureByBoutique(idBoutique);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{id}/rupture")
    public ResponseEntity<Boolean> isStockEnRupture(@PathVariable Integer id) {
        boolean isEnRupture = stockService.isStockEnRupture(id);
        return new ResponseEntity<>(isEnRupture, HttpStatus.OK);
    }

    @GetMapping("/{id}/epuise")
    public ResponseEntity<Boolean> isStockEpuise(@PathVariable Integer id) {
        boolean isEpuise = stockService.isStockEpuise(id);
        return new ResponseEntity<>(isEpuise, HttpStatus.OK);
    }

    @GetMapping("/boutique/{idBoutique}/count")
    public ResponseEntity<Long> countProduitsEnStockByBoutique(@PathVariable Integer idBoutique) {
        Long count = stockService.countProduitsEnStockByBoutique(idBoutique);
        return new ResponseEntity<>(count != null ? count : 0L, HttpStatus.OK);
    }

    @GetMapping("/boutique/{idBoutique}/valeur")
    public ResponseEntity<Long> getValeurTotaleStockByBoutique(@PathVariable Integer idBoutique) {
        Long valeur = stockService.getValeurTotaleStockByBoutique(idBoutique);
        return new ResponseEntity<>(valeur != null ? valeur : 0L, HttpStatus.OK);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Stock>> getStocksByStatut(@PathVariable String statut) {
        List<Stock> stocks = stockService.getStocksByStatut(statut);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/boutique/{idBoutique}/statut/{statut}")
    public ResponseEntity<List<Stock>> getStocksByBoutiqueAndStatut(@PathVariable Integer idBoutique, @PathVariable String statut) {
        List<Stock> stocks = stockService.getStocksByBoutiqueAndStatut(idBoutique, statut);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }
}