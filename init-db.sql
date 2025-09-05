-- Script d'initialisation des bases de données pour les microservices

-- Création des bases de données pour chaque service
CREATE DATABASE user_db;
CREATE DATABASE boutique_db;
CREATE DATABASE stock_db;
CREATE DATABASE commande_db;
CREATE DATABASE communication_db;
CREATE DATABASE evaluation_db;
CREATE DATABASE livraison_db;
CREATE DATABASE paiement_db;
CREATE DATABASE analytics_db;

-- Accordage des privilèges
GRANT ALL PRIVILEGES ON DATABASE user_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE boutique_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE stock_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE commande_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE communication_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE evaluation_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE livraison_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE paiement_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE analytics_db TO postgres;
