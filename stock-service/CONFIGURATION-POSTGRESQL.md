# 🗄️ Configuration PostgreSQL - Service Stock

## 📋 Prérequis

1. **PostgreSQL installé** sur votre machine
2. **Utilisateur postgres** avec mot de passe `123456789`
3. **Base de données** `stock_service` créée

## 🚀 Étapes de configuration

### 1. Créer la base de données

#### Option A: Via psql

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Créer la base de données
CREATE DATABASE stock_service;

# Vérifier
\l

# Quitter
\q
```

#### Option B: Via script

```bash
# Exécuter le script fourni
psql -U postgres -f create-database.sql
```

### 2. Configuration du service

Le fichier `application.properties` est déjà configuré :

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/stock_service
spring.datasource.username=postgres
spring.datasource.password=123456789
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 3. Démarrer le service

```bash
# Windows
start-with-postgres.bat

# Ou manuellement
mvn spring-boot:run
```

## 📊 Tables créées automatiquement

JPA va créer automatiquement les tables suivantes :

### Table `stock`

```sql
CREATE TABLE stock (
    id_stock SERIAL PRIMARY KEY,
    id_produit INTEGER,
    id_boutique INTEGER,
    quantite_disponible INTEGER,
    seuil_critique INTEGER,
    capacite_maximale INTEGER,
    derniere_maj TIMESTAMP,
    statut VARCHAR(50),
    location VARCHAR(100)
);
```

### Table `stock_transaction`

```sql
CREATE TABLE stock_transaction (
    id_transaction SERIAL PRIMARY KEY,
    id_produit INTEGER,
    id_stock INTEGER,
    type VARCHAR(50),
    quantity INTEGER,
    transaction_date TIMESTAMP,
    prix_unitaire DECIMAL(10,2),
    revenu_total DECIMAL(10,2),
    notes TEXT
);
```

## 🔍 Vérification

### 1. Vérifier la connexion

```bash
# Se connecter à la base
psql -U postgres -d stock_service

# Lister les tables
\dt

# Voir la structure d'une table
\d stock
\d stock_transaction
```

### 2. Vérifier le service

```bash
# Tester l'API
curl http://localhost:8084/api/stocks
```

## 🐛 Dépannage

### Problème : "Connection refused"

```bash
# Vérifier que PostgreSQL est démarré
# Windows : Services > PostgreSQL
# Linux : sudo systemctl start postgresql
```

### Problème : "Database does not exist"

```bash
# Créer la base de données
CREATE DATABASE stock_service;
```

### Problème : "Authentication failed"

```bash
# Vérifier le mot de passe dans application.properties
spring.datasource.password=123456789
```

### Problème : "Permission denied"

```bash
# Donner les permissions
GRANT ALL PRIVILEGES ON DATABASE stock_service TO postgres;
```

## ✅ Vérification finale

1. **PostgreSQL** : Base `stock_service` créée
2. **Service** : Démarré sur le port 8084
3. **Tables** : Créées automatiquement par JPA
4. **APIs** : Accessibles sur http://localhost:8084/api/stocks

## 🎉 Félicitations !

Votre service stock est maintenant configuré avec PostgreSQL ! 🚀
