-- Script d'initialisation de la base de données pour le service Stock
-- Ce script est exécuté automatiquement par Spring Boot

-- Table des stocks
CREATE TABLE IF NOT EXISTS stock (
    id_stock SERIAL PRIMARY KEY,
    id_produit INTEGER NOT NULL,
    id_boutique INTEGER NOT NULL,
    quantite_disponible INTEGER NOT NULL DEFAULT 0,
    seuil_critique INTEGER NOT NULL DEFAULT 10,
    capacite_maximale INTEGER NOT NULL DEFAULT 1000,
    derniere_maj TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    statut VARCHAR(50) NOT NULL DEFAULT 'ACTIF',
    location VARCHAR(100),
    UNIQUE(id_produit, id_boutique)
);

-- Table des transactions de stock
CREATE TABLE IF NOT EXISTS stock_transaction (
    id_transaction SERIAL PRIMARY KEY,
    id_produit INTEGER NOT NULL,
    id_stock INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('ENTREE', 'SORTIE', 'AJUSTEMENT', 'RETOUR')),
    quantity INTEGER NOT NULL,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    prix_unitaire DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    revenu_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    notes TEXT,
    FOREIGN KEY (id_stock) REFERENCES stock(id_stock) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_stock_produit_boutique ON stock(id_produit, id_boutique);
CREATE INDEX IF NOT EXISTS idx_stock_boutique ON stock(id_boutique);
CREATE INDEX IF NOT EXISTS idx_stock_produit ON stock(id_produit);
CREATE INDEX IF NOT EXISTS idx_stock_quantite ON stock(quantite_disponible);
CREATE INDEX IF NOT EXISTS idx_stock_statut ON stock(statut);

CREATE INDEX IF NOT EXISTS idx_transaction_produit ON stock_transaction(id_produit);
CREATE INDEX IF NOT EXISTS idx_transaction_stock ON stock_transaction(id_stock);
CREATE INDEX IF NOT EXISTS idx_transaction_type ON stock_transaction(type);
CREATE INDEX IF NOT EXISTS idx_transaction_date ON stock_transaction(transaction_date);

-- Données de test (optionnel)
INSERT INTO stock (id_produit, id_boutique, quantite_disponible, seuil_critique, capacite_maximale, statut, location) 
VALUES 
(1, 1, 100, 10, 500, 'ACTIF', 'A1-B1-C1'),
(2, 1, 50, 5, 200, 'ACTIF', 'A1-B1-C2'),
(3, 1, 5, 10, 100, 'ACTIF', 'A1-B2-C1'),
(1, 2, 75, 15, 300, 'ACTIF', 'A2-B1-C1'),
(2, 2, 0, 5, 150, 'EPUISE', 'A2-B1-C2')
ON CONFLICT (id_produit, id_boutique) DO NOTHING;

-- Transactions de test
INSERT INTO stock_transaction (id_produit, id_stock, type, quantity, prix_unitaire, revenu_total, notes)
SELECT 
    s.id_produit,
    s.id_stock,
    'ENTREE',
    100,
    10.50,
    1050.00,
    'Stock initial'
FROM stock s
WHERE s.id_produit = 1 AND s.id_boutique = 1
ON CONFLICT DO NOTHING; 