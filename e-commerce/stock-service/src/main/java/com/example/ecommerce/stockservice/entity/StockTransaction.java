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

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "id_stock")
    private Integer idStock;

    @Column(name = "type")
    private String type; // "ENTREE", "SORTIE", "AJUSTEMENT", "RETOUR"

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "revenu_total")
    private BigDecimal revenuTotal;

    @Column(name = "notes")
    private String notes;

    // Constructeurs
    public StockTransaction() {
    }

    public StockTransaction(Integer idProduit, Integer idStock, String type, Integer quantity,
            BigDecimal prixUnitaire, String notes) {
        this.idProduit = idProduit;
        this.idStock = idStock;
        this.type = type;
        this.quantity = quantity;
        this.prixUnitaire = prixUnitaire;
        this.notes = notes;
        this.transactionDate = LocalDateTime.now();
        this.revenuTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters et Setters
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

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
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

    @Override
    public String toString() {
        return "StockTransaction{" +
                "idTransaction=" + idTransaction +
                ", idProduit=" + idProduit +
                ", idStock=" + idStock +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", transactionDate=" + transactionDate +
                ", prixUnitaire=" + prixUnitaire +
                ", revenuTotal=" + revenuTotal +
                ", notes='" + notes + '\'' +
                '}';
    }
}