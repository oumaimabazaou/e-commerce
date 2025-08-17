package com.example.ecommerce.stockservice.repository;

import com.example.ecommerce.stockservice.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {

    List<StockTransaction> findByIdProduitOrderByTransactionDateDesc(Integer idProduit);

    List<StockTransaction> findByStock_IdStockOrderByTransactionDateDesc(Integer idStock);

    List<StockTransaction> findByTypeOrderByTransactionDateDesc(String type);

    List<StockTransaction> findByIdProduitAndTypeOrderByTransactionDateDesc(Integer idProduit, String type);

    List<StockTransaction> findByStock_IdStockAndTypeOrderByTransactionDateDesc(Integer idStock, String type);

    @Query("SELECT st FROM StockTransaction st WHERE st.transactionDate BETWEEN :dateDebut AND :dateFin")
    List<StockTransaction> findTransactionsByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    @Query("SELECT st FROM StockTransaction st WHERE st.idProduit = :idProduit AND st.transactionDate BETWEEN :dateDebut AND :dateFin")
    List<StockTransaction> findTransactionsByProduitAndDateRange(Integer idProduit, LocalDateTime dateDebut, LocalDateTime dateFin);

    @Query("SELECT COALESCE(SUM(st.revenuTotal), 0) FROM StockTransaction st WHERE st.idProduit = :idProduit")
    BigDecimal getRevenuTotalByProduit(Integer idProduit);

    @Query("SELECT COALESCE(SUM(st.revenuTotal), 0) FROM StockTransaction st WHERE st.stock.idBoutique = :idBoutique")
    BigDecimal getRevenuTotalByBoutique(Integer idBoutique);

    @Query("SELECT COALESCE(SUM(st.revenuTotal), 0) FROM StockTransaction st WHERE st.transactionDate BETWEEN :dateDebut AND :dateFin")
    BigDecimal getRevenuTotalByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    @Query("SELECT st FROM StockTransaction st WHERE st.revenuTotal >= :montant")
    List<StockTransaction> findTransactionsByRevenuMin(BigDecimal montant);

    @Query("SELECT st.type, COUNT(st) FROM StockTransaction st GROUP BY st.type")
    List<Object[]> countTransactionsByType();

    @Query("SELECT st FROM StockTransaction st WHERE st.stock.idBoutique = :idBoutique")
    List<StockTransaction> findByStock_IdBoutique(Integer idBoutique);
}