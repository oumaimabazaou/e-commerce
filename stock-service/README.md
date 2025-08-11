# Service Stock - E-commerce Platform

## Description

Le service Stock est un microservice Spring Boot qui gère la gestion des stocks et des transactions de stock pour la plateforme e-commerce. Il fournit des API REST complètes pour la gestion des stocks, le suivi des transactions et la génération de rapports.

## Fonctionnalités

### Gestion des Stocks

- ✅ Création, lecture, mise à jour et suppression des stocks
- ✅ Gestion des quantités disponibles
- ✅ Définition de seuils critiques
- ✅ Suivi des capacités maximales
- ✅ Gestion des emplacements de stockage
- ✅ Statuts de stock (ACTIF, INACTIF, EPUISE, etc.)

### Gestion des Transactions

- ✅ Transactions d'entrée (réception de marchandises)
- ✅ Transactions de sortie (ventes, consommations)
- ✅ Transactions d'ajustement (corrections d'inventaire)
- ✅ Transactions de retour (retours clients)
- ✅ Suivi des prix unitaires et revenus totaux
- ✅ Historique complet des mouvements

### Alertes et Surveillance

- ✅ Détection automatique des stocks en rupture
- ✅ Alertes de seuil critique
- ✅ Surveillance des stocks épuisés
- ✅ Rapports d'alerte par boutique

### Analytics et Rapports

- ✅ Statistiques de stock par boutique
- ✅ Calcul des revenus totaux
- ✅ Analyse des mouvements de stock
- ✅ Rapports temporels
- ✅ Statistiques globales

## Architecture

### Entités

#### Stock

```java
- idStock (PK)
- idProduit (FK)
- idBoutique (FK)
- quantiteDisponible
- seuilCritique
- capaciteMaximale
- derniereMaj
- statut
- location
```

#### StockTransaction

```java
- idTransaction (PK)
- idProduit (FK)
- idStock (FK)
- type (ENTREE, SORTIE, AJUSTEMENT, RETOUR)
- quantity
- transactionDate
- prixUnitaire
- revenuTotal
- notes
```

### Services

#### StockService

- Gestion CRUD des stocks
- Opérations de quantité (ajouter, retirer, ajuster)
- Surveillance des alertes
- Calculs statistiques

#### StockTransactionService

- Gestion CRUD des transactions
- Analytics et rapports
- Calculs de revenus
- Statistiques par type

### Contrôleurs

#### StockController (`/api/stocks`)

- Endpoints CRUD pour les stocks
- Gestion des quantités
- Alertes et surveillance
- Statistiques de base

#### StockTransactionController (`/api/stock-transactions`)

- Endpoints CRUD pour les transactions
- Filtrage par type, produit, boutique
- Analytics et rapports
- Calculs de revenus

#### StockReportController (`/api/stock-reports`)

- Rapports complets de stock
- Rapports de boutique
- Rapports temporels
- Alertes et notifications
- Statistiques globales

## API Endpoints

### Stocks

#### CRUD Basique

- `POST /api/stocks` - Créer un stock
- `GET /api/stocks/{id}` - Obtenir un stock par ID
- `PUT /api/stocks/{id}` - Mettre à jour un stock
- `DELETE /api/stocks/{id}` - Supprimer un stock

#### Recherche

- `GET /api/stocks/produit/{idProduit}/boutique/{idBoutique}` - Stock d'un produit dans une boutique
- `GET /api/stocks/boutique/{idBoutique}` - Tous les stocks d'une boutique
- `GET /api/stocks/produit/{idProduit}` - Tous les stocks d'un produit

#### Gestion des Quantités

- `POST /api/stocks/{id}/ajouter` - Ajouter de la quantité
- `POST /api/stocks/{id}/retirer` - Retirer de la quantité
- `POST /api/stocks/{id}/ajuster` - Ajuster la quantité

#### Alertes

- `GET /api/stocks/rupture` - Stocks en rupture
- `GET /api/stocks/rupture/boutique/{idBoutique}` - Stocks en rupture d'une boutique
- `GET /api/stocks/{id}/rupture` - Vérifier si un stock est en rupture
- `GET /api/stocks/{id}/epuise` - Vérifier si un stock est épuisé

#### Statistiques

- `GET /api/stocks/boutique/{idBoutique}/count` - Nombre de produits en stock
- `GET /api/stocks/boutique/{idBoutique}/valeur` - Valeur totale du stock
- `GET /api/stocks/statut/{statut}` - Stocks par statut

### Transactions

#### CRUD Basique

- `POST /api/stock-transactions` - Créer une transaction
- `GET /api/stock-transactions/{id}` - Obtenir une transaction par ID
- `PUT /api/stock-transactions/{id}` - Mettre à jour une transaction
- `DELETE /api/stock-transactions/{id}` - Supprimer une transaction

#### Recherche

- `GET /api/stock-transactions/produit/{idProduit}` - Transactions d'un produit
- `GET /api/stock-transactions/stock/{idStock}` - Transactions d'un stock
- `GET /api/stock-transactions/boutique/{idBoutique}` - Transactions d'une boutique
- `GET /api/stock-transactions/type/{type}` - Transactions par type

#### Analytics

- `GET /api/stock-transactions/produit/{idProduit}/revenu` - Revenu total d'un produit
- `GET /api/stock-transactions/boutique/{idBoutique}/revenu` - Revenu total d'une boutique
- `GET /api/stock-transactions/revenu/periode` - Revenu total par période
- `GET /api/stock-transactions/count/par-type` - Nombre de transactions par type

#### Transactions Spécialisées

- `GET /api/stock-transactions/produit/{idProduit}/entrees` - Entrées d'un produit
- `GET /api/stock-transactions/produit/{idProduit}/sorties` - Sorties d'un produit
- `GET /api/stock-transactions/produit/{idProduit}/ajustements` - Ajustements d'un produit
- `GET /api/stock-transactions/produit/{idProduit}/retours` - Retours d'un produit

### Rapports

#### Rapports de Stock

- `GET /api/stock-reports/stock/{idStock}` - Rapport complet d'un stock
- `GET /api/stock-reports/boutique/{idBoutique}` - Rapport complet d'une boutique
- `GET /api/stock-reports/produit/{idProduit}` - Rapport d'un produit

#### Rapports Temporels

- `GET /api/stock-reports/periode` - Rapport pour une période
- `GET /api/stock-reports/produit/{idProduit}/periode` - Rapport d'un produit pour une période

#### Alertes

- `GET /api/stock-reports/alertes` - Rapport des alertes globales
- `GET /api/stock-reports/alertes/boutique/{idBoutique}` - Alertes d'une boutique

#### Statistiques Globales

- `GET /api/stock-reports/statistiques-globales` - Statistiques globales du système

## Configuration

### Base de données

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_stock
spring.datasource.username=postgres
spring.datasource.password=password
```

### Port du service

```properties
server.port=8084
```

### Service Discovery

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

## Démarrage

1. **Prérequis**

   - Java 17
   - Maven
   - PostgreSQL
   - Eureka Server

2. **Base de données**

   ```sql
   -- Créer la base de données
   CREATE DATABASE stock_service;

   -- Ou utiliser le script fourni
   -- psql -U postgres -f create-database.sql
   ```

3. **Compilation et démarrage**

   ```bash
   cd stock-service
   mvn clean install
   mvn spring-boot:run
   ```

4. **Vérification**
   - Service disponible sur : http://localhost:8084
   - Actuator : http://localhost:8084/actuator
   - API Documentation : http://localhost:8084/swagger-ui.html

## Tests

### Tests Unitaires

```bash
mvn test
```

### Tests d'Intégration

```bash
mvn verify
```

## Monitoring

### Métriques disponibles

- Nombre de stocks par boutique
- Quantités disponibles
- Transactions par type
- Revenus totaux
- Alertes de rupture

### Health Checks

- `GET /actuator/health` - État du service
- `GET /actuator/metrics` - Métriques disponibles
- `GET /actuator/info` - Informations du service

## Intégration

### Avec les autres services

- **Boutique Service** : Récupération des informations de boutique
- **User Service** : Authentification et autorisation
- **API Gateway** : Routage des requêtes

### Communication

- REST APIs pour les opérations synchrones
- Event-driven pour les notifications d'alerte
- Service discovery via Eureka

## Sécurité

### Authentification

- JWT tokens (via API Gateway)
- Rôles utilisateur (ADMIN, VENDEUR, CLIENT)

### Autorisation

- Vendeurs : Gestion de leurs stocks uniquement
- Admins : Accès complet
- Clients : Lecture seule des stocks disponibles

## Performance

### Optimisations

- Cache Redis pour les stocks fréquemment consultés
- Pagination pour les listes volumineuses
- Indexation de la base de données
- Compression des réponses JSON

### Métriques de Performance

- Temps de réponse moyen < 200ms
- Throughput > 1000 req/sec
- Disponibilité > 99.9%

## Maintenance

### Sauvegarde

- Sauvegarde quotidienne de la base de données
- Archivage des transactions anciennes
- Rotation des logs

### Mise à jour

- Déploiement sans interruption de service
- Rollback automatique en cas d'erreur
- Tests de régression automatisés

## Support

### Logs

- Logs structurés en JSON
- Niveaux : DEBUG, INFO, WARN, ERROR
- Rotation automatique des fichiers

### Debugging

- Endpoints de debug disponibles en développement
- Traçage des requêtes avec correlation ID
- Métriques détaillées pour le troubleshooting
