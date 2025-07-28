package com.example.ecommerce.stockservice.repository;

import com.example.ecommerce.stockservice.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {

    // Trouver toutes les transactions d'un produit
    List<StockTransaction> findByIdProduitOrderByTransactionDateDesc(Integer idProduit);

    // Trouver toutes les transactions d'un stock
    List<StockTransaction> findByIdStockOrderByTransactionDateDesc(Integer idStock);

    // Trouver toutes les transactions d'une boutique
    @Query("SELECT st FROM StockTransaction st JOIN Stock s ON st.idStock = s.idStock WHERE s.idBoutique = :idBoutique ORDER BY st.transactionDate DESC")
    List<StockTransaction> findTransactionsByBoutique(@Param("idBoutique") Integer idBoutique);

    // Trouver les transactions par type
    List<StockTransaction> findByTypeOrderByTransactionDateDesc(String type);

    // Trouver les transactions d'un produit par type
    List<StockTransaction> findByIdProduitAndTypeOrderByTransactionDateDesc(Integer idProduit, String type);

    // Trouver les transactions d'un stock par type
    List<StockTransaction> findByIdStockAndTypeOrderByTransactionDateDesc(Integer idStock, String type);

    // Trouver les transactions dans une période donnée
    @Query("SELECT st FROM StockTransaction st WHERE st.transactionDate BETWEEN :dateDebut AND :dateFin ORDER BY st.transactionDate DESC")
    List<StockTransaction> findTransactionsByDateRange(@Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    // Trouver les transactions d'un produit dans une période donnée
    @Query("SELECT st FROM StockTransaction st WHERE st.idProduit = :idProduit AND st.transactionDate BETWEEN :dateDebut AND :dateFin ORDER BY st.transactionDate DESC")
    List<StockTransaction> findTransactionsByProduitAndDateRange(@Param("idProduit") Integer idProduit,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    // Calculer le revenu total des transactions d'un produit
    @Query("SELECT SUM(st.revenuTotal) FROM StockTransaction st WHERE st.idProduit = :idProduit")
    BigDecimal getRevenuTotalByProduit(@Param("idProduit") Integer idProduit);

    // Calculer le revenu total des transactions d'une boutique
    @Query("SELECT SUM(st.revenuTotal) FROM StockTransaction st JOIN Stock s ON st.idStock = s.idStock WHERE s.idBoutique = :idBoutique")
    BigDecimal getRevenuTotalByBoutique(@Param("idBoutique") Integer idBoutique);

    // Calculer le revenu total des transactions dans une période donnée
    @Query("SELECT SUM(st.revenuTotal) FROM StockTransaction st WHERE st.transactionDate BETWEEN :dateDebut AND :dateFin")
    BigDecimal getRevenuTotalByDateRange(@Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    // Trouver les transactions avec revenu supérieur à un montant
    @Query("SELECT st FROM StockTransaction st WHERE st.revenuTotal > :montant ORDER BY st.revenuTotal DESC")
    List<StockTransaction> findTransactionsByRevenuMin(@Param("montant") BigDecimal montant);

    // Compter le nombre de transactions par type
    @Query("SELECT st.type, COUNT(st) FROM StockTransaction st GROUP BY st.type")
    List<Object[]> countTransactionsByType();
}