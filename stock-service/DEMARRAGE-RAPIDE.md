# 🚀 Démarrage Rapide - Service Stock

## ✅ Problème résolu !

Le problème était dans les versions Spring Cloud :

- ❌ Version Spring Cloud `2024.0.4` (n'existe pas)
- ✅ Version Spring Cloud `2023.0.4` (correcte)
- ❌ Java 24 (trop récent)
- ✅ Java 17 (stable)

## 🎯 Comment démarrer le service

### Option 1: Script automatique

```bash
# Windows
start.bat

# Linux/Mac
chmod +x start.sh
./start.sh
```

### Option 2: Manuel

```bash
# Compiler
mvn clean compile

# Démarrer
mvn spring-boot:run
```

## 🌐 URLs disponibles

Une fois le service démarré, vous pouvez accéder à :

- **Service principal** : http://localhost:8084
- **API Stocks** : http://localhost:8084/api/stocks
- **API Transactions** : http://localhost:8084/api/stock-transactions
- **API Rapports** : http://localhost:8084/api/stock-reports
- **Eureka Server** : http://localhost:8761

## 🧪 Tester les APIs

Exécutez le script de test :

```bash
test-api.bat
```

## 📊 Configuration

### Mode Normal (PostgreSQL + Eureka)

- Base de données : PostgreSQL
- Port : 8084
- Eureka : Activé
- Service Discovery : Activé

## 🔧 Prérequis

1. **Eureka Server** doit être démarré sur le port 8761
2. **PostgreSQL** doit être configuré
3. **Java 17** installé

## 🐛 Dépannage

### Problème : "Eureka Server non trouvé"

```bash
# Démarrer Eureka Server d'abord
cd ../eureka-server
mvn spring-boot:run
```

### Problème : "PostgreSQL non connecté"

```bash
# Vérifier la configuration dans application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_stock
```

### Problème : "Port déjà utilisé"

```bash
# Changer le port dans application.properties
server.port=8085
```

## ✅ Vérification

Le service fonctionne si vous voyez :

```
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.e.e.s.StockServiceApplication : Started StockServiceApplication
```

Et dans Eureka :

- Service `stock-service` enregistré
- Status : UP

## 🎉 Félicitations !

Votre service stock est maintenant opérationnel avec Eureka ! 🚀
