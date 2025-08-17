package com.example.ecommerce.stockservice.repository;

import com.example.ecommerce.stockservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findByIdProduitAndIdBoutique(Integer idProduit, Integer idBoutique);

    List<Stock> findByIdBoutique(Integer idBoutique);

    List<Stock> findByIdProduit(Integer idProduit);

    @Query("SELECT s FROM Stock s WHERE s.quantiteDisponible <= s.seuilCritique")
    List<Stock> findStocksEnRupture();

    @Query("SELECT s FROM Stock s WHERE s.idBoutique = :idBoutique AND s.quantiteDisponible <= s.seuilCritique")
    List<Stock> findStocksEnRuptureByBoutique(@Param("idBoutique") Integer idBoutique);

    List<Stock> findByStatut(String statut);

    List<Stock> findByIdBoutiqueAndStatut(Integer idBoutique, String statut);

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.idBoutique = :idBoutique AND s.quantiteDisponible > 0")
    Long countProduitsEnStockByBoutique(@Param("idBoutique") Integer idBoutique);

    @Query("SELECT COALESCE(SUM(s.quantiteDisponible * s.prixUnitaire), 0) FROM Stock s WHERE s.idBoutique = :idBoutique")
    BigDecimal getValeurTotaleStockByBoutique(@Param("idBoutique") Integer idBoutique);
}