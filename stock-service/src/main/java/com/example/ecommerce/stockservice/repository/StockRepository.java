package com.example.ecommerce.stockservice.repository;

import com.example.ecommerce.stockservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // Trouver le stock par produit et boutique
    Optional<Stock> findByIdProduitAndIdBoutique(Integer idProduit, Integer idBoutique);

    // Trouver tous les stocks d'une boutique
    List<Stock> findByIdBoutique(Integer idBoutique);

    // Trouver tous les stocks d'un produit
    List<Stock> findByIdProduit(Integer idProduit);

    // Trouver les stocks avec quantité disponible inférieure au seuil critique
    @Query("SELECT s FROM Stock s WHERE s.quantiteDisponible <= s.seuilCritique")
    List<Stock> findStocksEnRupture();

    // Trouver les stocks d'une boutique avec quantité disponible inférieure au
    // seuil critique
    @Query("SELECT s FROM Stock s WHERE s.idBoutique = :idBoutique AND s.quantiteDisponible <= s.seuilCritique")
    List<Stock> findStocksEnRuptureByBoutique(@Param("idBoutique") Integer idBoutique);

    // Trouver les stocks par statut
    List<Stock> findByStatut(String statut);

    // Trouver les stocks d'une boutique par statut
    List<Stock> findByIdBoutiqueAndStatut(Integer idBoutique, String statut);

    // Compter le nombre de produits en stock pour une boutique
    @Query("SELECT COUNT(s) FROM Stock s WHERE s.idBoutique = :idBoutique AND s.quantiteDisponible > 0")
    Long countProduitsEnStockByBoutique(@Param("idBoutique") Integer idBoutique);

    // Calculer la valeur totale du stock d'une boutique
    @Query("SELECT SUM(s.quantiteDisponible) FROM Stock s WHERE s.idBoutique = :idBoutique")
    Long getValeurTotaleStockByBoutique(@Param("idBoutique") Integer idBoutique);
}