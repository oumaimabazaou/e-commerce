package com.example.ecommerce.stockservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transaction")
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Integer idTransaction;

    @Column(name = "id_produit", nullable = false)
    private Integer idProduit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stock", nullable = false)
    private Stock stock;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "prix_unitaire", nullable = false)
    private BigDecimal prixUnitaire;

    @Column(name = "revenu_total", nullable = false)
    private BigDecimal revenuTotal;

    @Column(name = "notes")
    private String notes;

    public StockTransaction() {
    }

    public StockTransaction(Integer idProduit, Stock stock, String type, Integer quantity, BigDecimal prixUnitaire, String notes) {
        if (idProduit == null || stock == null || stock.getIdStock() == null || type == null || quantity == null || quantity <= 0 ||
                prixUnitaire == null || prixUnitaire.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les champs idProduit, stock, type, quantity, et prixUnitaire sont requis et doivent être valides.");
        }
        this.idProduit = idProduit;
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.prixUnitaire = prixUnitaire;
        this.notes = notes;
        this.transactionDate = LocalDateTime.now();
        this.revenuTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantity));
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getRevenuTotal() {
        return revenuTotal;
    }

    public void setRevenuTotal(BigDecimal revenuTotal) {
        this.revenuTotal = revenuTotal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}