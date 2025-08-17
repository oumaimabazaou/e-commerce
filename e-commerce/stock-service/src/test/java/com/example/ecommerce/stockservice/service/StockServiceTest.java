package com.example.ecommerce.stockservice.service;

import com.example.ecommerce.stockservice.entity.Stock;
import com.example.ecommerce.stockservice.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    private Stock testStock;

    @BeforeEach
    void setUp() {
        // Créer un stock de test
        testStock = new Stock();
        testStock.setIdProduit(1);
        testStock.setIdBoutique(1);
        testStock.setQuantiteDisponible(100);
        testStock.setSeuilCritique(10);
        testStock.setCapaciteMaximale(500);
        testStock.setStatut("ACTIF");
        testStock.setLocation("A1-B2-C3");
    }

    @Test
    void testCreateStock() {
        // When
        Stock createdStock = stockService.createStock(testStock);

        // Then
        assertNotNull(createdStock.getIdStock());
        assertEquals(testStock.getIdProduit(), createdStock.getIdProduit());
        assertEquals(testStock.getIdBoutique(), createdStock.getIdBoutique());
        assertEquals(testStock.getQuantiteDisponible(), createdStock.getQuantiteDisponible());
    }

    @Test
    void testGetStockById() {
        // Given
        Stock savedStock = stockService.createStock(testStock);

        // When
        Optional<Stock> foundStock = stockService.getStockById(savedStock.getIdStock());

        // Then
        assertTrue(foundStock.isPresent());
        assertEquals(savedStock.getIdStock(), foundStock.get().getIdStock());
    }

    @Test
    void testGetStockByProduitAndBoutique() {
        // Given
        Stock savedStock = stockService.createStock(testStock);

        // When
        Optional<Stock> foundStock = stockService.getStockByProduitAndBoutique(
                testStock.getIdProduit(), testStock.getIdBoutique());

        // Then
        assertTrue(foundStock.isPresent());
        assertEquals(savedStock.getIdStock(), foundStock.get().getIdStock());
    }

    @Test
    void testAjouterQuantite() {
        // Given
        Stock savedStock = stockService.createStock(testStock);
        int quantiteAjoutee = 50;
        BigDecimal prixUnitaire = new BigDecimal("10.50");

        // When
        Stock updatedStock = stockService.ajouterQuantite(
                savedStock.getIdStock(), quantiteAjoutee, prixUnitaire, "Test ajout");

        // Then
        assertEquals(testStock.getQuantiteDisponible() + quantiteAjoutee, updatedStock.getQuantiteDisponible());
    }

    @Test
    void testRetirerQuantite() {
        // Given
        Stock savedStock = stockService.createStock(testStock);
        int quantiteRetiree = 30;
        BigDecimal prixUnitaire = new BigDecimal("15.00");

        // When
        Stock updatedStock = stockService.retirerQuantite(
                savedStock.getIdStock(), quantiteRetiree, prixUnitaire, "Test retrait");

        // Then
        assertEquals(testStock.getQuantiteDisponible() - quantiteRetiree, updatedStock.getQuantiteDisponible());
    }

    @Test
    void testRetirerQuantiteInsufficiente() {
        // Given
        Stock savedStock = stockService.createStock(testStock);
        int quantiteRetiree = 150; // Plus que disponible
        BigDecimal prixUnitaire = new BigDecimal("15.00");

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            stockService.retirerQuantite(savedStock.getIdStock(), quantiteRetiree, prixUnitaire, "Test retrait");
        });
    }

    @Test
    void testGetStocksEnRupture() {
        // Given
        Stock stockEnRupture = new Stock();
        stockEnRupture.setIdProduit(2);
        stockEnRupture.setIdBoutique(1);
        stockEnRupture.setQuantiteDisponible(5); // En dessous du seuil critique
        stockEnRupture.setSeuilCritique(10);
        stockEnRupture.setCapaciteMaximale(500);
        stockEnRupture.setStatut("ACTIF");
        stockEnRupture.setLocation("A2-B3-C4");

        stockService.createStock(stockEnRupture);

        // When
        List<Stock> stocksEnRupture = stockService.getStocksEnRupture();

        // Then
        assertFalse(stocksEnRupture.isEmpty());
        assertTrue(stocksEnRupture.stream().anyMatch(stock -> stock.getIdProduit().equals(2)));
    }

    @Test
    void testIsStockEnRupture() {
        // Given
        Stock stockEnRupture = new Stock();
        stockEnRupture.setIdProduit(3);
        stockEnRupture.setIdBoutique(1);
        stockEnRupture.setQuantiteDisponible(5);
        stockEnRupture.setSeuilCritique(10);
        stockEnRupture.setCapaciteMaximale(500);
        stockEnRupture.setStatut("ACTIF");
        stockEnRupture.setLocation("A3-B4-C5");

        Stock savedStock = stockService.createStock(stockEnRupture);

        // When
        boolean isEnRupture = stockService.isStockEnRupture(savedStock.getIdStock());

        // Then
        assertTrue(isEnRupture);
    }

    @Test
    void testIsStockEpuise() {
        // Given
        Stock stockEpuise = new Stock();
        stockEpuise.setIdProduit(4);
        stockEpuise.setIdBoutique(1);
        stockEpuise.setQuantiteDisponible(0);
        stockEpuise.setSeuilCritique(10);
        stockEpuise.setCapaciteMaximale(500);
        stockEpuise.setStatut("ACTIF");
        stockEpuise.setLocation("A4-B5-C6");

        Stock savedStock = stockService.createStock(stockEpuise);

        // When
        boolean isEpuise = stockService.isStockEpuise(savedStock.getIdStock());

        // Then
        assertTrue(isEpuise);
    }

    @Test
    void testUpdateStock() {
        // Given
        Stock savedStock = stockService.createStock(testStock);
        savedStock.setQuantiteDisponible(200);
        savedStock.setStatut("INACTIF");

        // When
        Stock updatedStock = stockService.updateStock(savedStock);

        // Then
        assertEquals(200, updatedStock.getQuantiteDisponible());
        assertEquals("INACTIF", updatedStock.getStatut());
    }

    @Test
    void testDeleteStock() {
        // Given
        Stock savedStock = stockService.createStock(testStock);

        // When
        stockService.deleteStock(savedStock.getIdStock());

        // Then
        Optional<Stock> deletedStock = stockService.getStockById(savedStock.getIdStock());
        assertFalse(deletedStock.isPresent());
    }
}