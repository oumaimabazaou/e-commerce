-- Script pour créer la base de données stock_service
-- Exécutez ce script dans PostgreSQL

-- Créer la base de données
CREATE DATABASE stock_service;

-- Se connecter à la base de données
\c stock_service;

-- Créer l'utilisateur si nécessaire (optionnel)
-- CREATE USER postgres WITH PASSWORD '123456789';

-- Donner les permissions
GRANT ALL PRIVILEGES ON DATABASE stock_service TO postgres;

-- Vérifier que la base est créée
SELECT current_database(); 