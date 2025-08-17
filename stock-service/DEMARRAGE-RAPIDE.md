# 🚀 Démarrage Rapide - Service Stock

## ✅ Problème résolu !

Le problème était dans le `pom.xml` :

- ❌ Version Spring Cloud `2024.0.4` (n'existe pas)
- ✅ Version Spring Cloud `2023.0.4` (correcte)
- ❌ Java 24 (trop récent)
- ✅ Java 17 (stable)

## 🎯 Comment démarrer le service

### Option 1: Mode Standalone (Recommandé)

```bash
# Windows
run-standalone.bat

# Linux/Mac
chmod +x run-standalone.sh
./run-standalone.sh
```

### Option 2: Manuel

```bash
# Compiler
mvn clean compile

# Démarrer en mode standalone
mvn spring-boot:run -Dspring-boot.run.profiles=standalone
```

## 🌐 URLs disponibles

Une fois le service démarré, vous pouvez accéder à :

- **Service principal** : http://localhost:8084
- **Console H2** : http://localhost:8084/h2-console
- **API Stocks** : http://localhost:8084/api/stocks
- **API Transactions** : http://localhost:8084/api/stock-transactions
- **API Rapports** : http://localhost:8084/api/stock-reports

## 🧪 Tester les APIs

Exécutez le script de test :

```bash
test-api.bat
```

## 📊 Données de test

Le service inclut des données de test :

- **Stocks** : 5 stocks pré-configurés
- **Transactions** : 1 transaction d'entrée initiale
- **Base de données** : H2 en mémoire (pas d'installation requise)

## 🔧 Configuration

### Mode Standalone (H2)

- Base de données : H2 en mémoire
- Port : 8084
- Eureka : Désactivé
- Profil : `standalone`

### Mode Normal (PostgreSQL)

- Base de données : PostgreSQL
- Port : 8084
- Eureka : Activé
- Profil : `default`

## 🐛 Dépannage

### Problème : "Port déjà utilisé"

```bash
# Changer le port dans application.properties
server.port=8085
```

### Problème : "Base de données non trouvée"

```bash
# Utiliser le mode standalone
mvn spring-boot:run -Dspring-boot.run.profiles=standalone
```

### Problème : "Dépendances non trouvées"

```bash
# Nettoyer et recompiler
mvn clean compile
```

## ✅ Vérification

Le service fonctionne si vous voyez :

```
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.e.e.s.StockServiceApplication : Started StockServiceApplication
```

## 🎉 Félicitations !

Votre service stock est maintenant opérationnel ! 🚀
