# Déploiement Docker - Application E-commerce

## Prérequis
- Docker Desktop installé
- Docker Compose installé
- Au moins 4GB de RAM disponible

## Architecture des Services
- **Eureka Server** (8761) - Service Discovery
- **API Gateway** (8099) - Point d'entrée unique
- **User Service** (8087) - Gestion des utilisateurs
- **Boutique Service** (8088) - Gestion des boutiques
- **Stock Service** (8096) - Gestion des stocks
- **Commande Service** (8090) - Gestion des commandes
- **Communication Service** (8091) - Notifications
- **Evaluation Service** (8092) - Évaluations
- **Livraison Service** (8089) - Gestion des livraisons
- **Paiement Service** (8100) - Paiements et abonnements
- **Analytics Service** (8093) - Recherche et analytics
- **PostgreSQL** (5432) - Base de données

## Déploiement Rapide

### Option 1: Script automatique
```bash
./deploy.bat
```

### Option 2: Étapes manuelles
```bash
# 1. Construire tous les services
./build-all.bat

# 2. Démarrer les conteneurs
docker-compose up --build -d

# 3. Vérifier le statut
docker-compose ps
```

## Commandes Utiles

### Gestion des conteneurs
```bash
# Démarrer tous les services
docker-compose up -d

# Arrêter tous les services
docker-compose down

# Redémarrer un service spécifique
docker-compose restart user-service

# Voir les logs d'un service
docker-compose logs -f user-service

# Voir les logs de tous les services
docker-compose logs -f
```

### Monitoring
```bash
# Statut des conteneurs
docker-compose ps

# Utilisation des ressources
docker stats

# Accès aux conteneurs
docker exec -it user-service bash
```

## Points d'accès
- **Eureka Dashboard**: http://localhost:8761 (admin/admin123)
- **API Gateway**: http://localhost:8099
- **PostgreSQL**: localhost:5432 (postgres/123456789)

## Bases de données créées
- user_db
- boutique_db
- stock_db
- commande_db
- communication_db
- evaluation_db
- livraison_db
- paiement_db
- analytics_db

## Dépannage

### Problèmes courants
1. **Port déjà utilisé**: Arrêtez les services locaux avant le déploiement
2. **Mémoire insuffisante**: Augmentez la mémoire allouée à Docker
3. **Services qui ne démarrent pas**: Vérifiez les logs avec `docker-compose logs`

### Reconstruction complète
```bash
docker-compose down -v
docker system prune -f
./deploy.bat
```
