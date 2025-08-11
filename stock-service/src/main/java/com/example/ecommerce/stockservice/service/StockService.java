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

    public Stock createStock(Stock stock) {
        if (stock.getQuantiteDisponible() == null || stock.getQuantiteDisponible() < 0) {
            throw new IllegalArgumentException("La quantité disponible doit être positive.");
        }
        stock.setDerniereMaj(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public Optional<Stock> getStockById(Integer idStock) {
        return stockRepository.findById(idStock);
    }

    public Optional<Stock> getStockByProduitAndBoutique(Integer idProduit, Integer idBoutique) {
        return stockRepository.findByIdProduitAndIdBoutique(idProduit, idBoutique);
    }

    public List<Stock> getStocksByBoutique(Integer idBoutique) {
        return stockRepository.findByIdBoutique(idBoutique);
    }

    public List<Stock> getStocksByProduit(Integer idProduit) {
        return stockRepository.findByIdProduit(idProduit);
    }

    public Stock updateStock(Stock stock) {
        if (stock.getIdStock() == null) {
            throw new IllegalArgumentException("L'ID du stock est requis pour la mise à jour.");
        }
        stock.setDerniereMaj(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public void deleteStock(Integer idStock) {
        stockRepository.deleteById(idStock);
    }

    public Stock ajouterQuantite(Integer idStock, Integer quantite, BigDecimal prixUnitaire, String notes) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité à ajouter doit être positive.");
        }
        if (prixUnitaire == null || prixUnitaire.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le prix unitaire doit être positif.");
        }
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            stock.setQuantiteDisponible(stock.getQuantiteDisponible() + quantite);
            stock.setDerniereMaj(LocalDateTime.now());

            StockTransaction transaction = new StockTransaction(
                    stock.getIdProduit(),
                    stock,
                    "ENTREE",
                    quantite,
                    prixUnitaire,
                    notes
            );
            stockTransactionRepository.save(transaction);

            return stockRepository.save(stock);
        }
        throw new IllegalArgumentException("Stock non trouvé avec l'ID: " + idStock);
    }

    public Stock retirerQuantite(Integer idStock, Integer quantite, BigDecimal prixUnitaire, String notes) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité à retirer doit être positive.");
        }
        if (prixUnitaire == null || prixUnitaire.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le prix unitaire doit être positif.");
        }
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            if (stock.getQuantiteDisponible() >= quantite) {
                stock.setQuantiteDisponible(stock.getQuantiteDisponible() - quantite);
                stock.setDerniereMaj(LocalDateTime.now());

                StockTransaction transaction = new StockTransaction(
                        stock.getIdProduit(),
                        stock,
                        "SORTIE",
                        quantite,
                        prixUnitaire,
                        notes
                );
                stockTransactionRepository.save(transaction);

                return stockRepository.save(stock);
            } else {
                throw new IllegalArgumentException("Quantité insuffisante en stock. Disponible: "
                        + stock.getQuantiteDisponible() + ", Demandé: " + quantite);
            }
        }
        throw new IllegalArgumentException("Stock non trouvé avec l'ID: " + idStock);
    }

    public Stock ajusterQuantite(Integer idStock, Integer nouvelleQuantite, String notes) {
        if (nouvelleQuantite == null || nouvelleQuantite < 0) {
            throw new IllegalArgumentException("La nouvelle quantité doit être positive.");
        }
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            int difference = nouvelleQuantite - stock.getQuantiteDisponible();
            stock.setQuantiteDisponible(nouvelleQuantite);
            stock.setDerniereMaj(LocalDateTime.now());

            BigDecimal prixUnitaire = stock.getPrixUnitaire();
            if (prixUnitaire == null) {
                throw new IllegalArgumentException("Le prix unitaire du stock n'est pas défini.");
            }

            String type = difference >= 0 ? "AJUSTEMENT_ENTREE" : "AJUSTEMENT_SORTIE";
            StockTransaction transaction = new StockTransaction(
                    stock.getIdProduit(),
                    stock,
                    type,
                    Math.abs(difference),
                    prixUnitaire,
                    notes + " (Ancienne quantité: " + (stock.getQuantiteDisponible() - difference) + ")"
            );
            stockTransactionRepository.save(transaction);

            return stockRepository.save(stock);
        }
        throw new IllegalArgumentException("Stock non trouvé avec l'ID: " + idStock);
    }

    public List<Stock> getStocksEnRupture() {
        return stockRepository.findStocksEnRupture();
    }

    public List<Stock> getStocksEnRuptureByBoutique(Integer idBoutique) {
        return stockRepository.findStocksEnRuptureByBoutique(idBoutique);
    }

    public boolean isStockEnRupture(Integer idStock) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        return stockOpt.map(stock -> stock.getQuantiteDisponible() <= stock.getSeuilCritique()).orElse(false);
    }

    public boolean isStockEpuise(Integer idStock) {
        Optional<Stock> stockOpt = stockRepository.findById(idStock);
        return stockOpt.map(stock -> stock.getQuantiteDisponible() <= 0).orElse(false);
    }

    public Long countProduitsEnStockByBoutique(Integer idBoutique) {
        return stockRepository.countProduitsEnStockByBoutique(idBoutique);
    }

    public Long getValeurTotaleStockByBoutique(Integer idBoutique) {
        BigDecimal valeur = stockRepository.getValeurTotaleStockByBoutique(idBoutique);
        return valeur != null ? valeur.longValue() : 0L;
    }

    public List<Stock> getStocksByStatut(String statut) {
        return stockRepository.findByStatut(statut);
    }

    public List<Stock> getStocksByBoutiqueAndStatut(Integer idBoutique, String statut) {
        return stockRepository.findByIdBoutiqueAndStatut(idBoutique, statut);
    }
}