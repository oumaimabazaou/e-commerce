@echo off
echo Building all microservices...

echo Building parent project...
call mvn clean install -DskipTests

echo Building eureka-server...
cd eureka-server
call mvn clean package -DskipTests
cd ..

echo Building api-gateway...
cd api-gateway
call mvn clean package -DskipTests
cd ..

echo Building config-server...
cd config-server
call mvn clean package -DskipTests
cd ..

echo Building user-service...
cd user-service
call mvn clean package -DskipTests
cd ..

echo Building boutique-service...
cd boutique-service
call mvn clean package -DskipTests
cd ..

echo Building stock-service...
cd stock-service
call mvn clean package -DskipTests
cd ..

echo Building commande-service...
cd commande-service
call mvn clean package -DskipTests
cd ..

echo Building Communication-service...
cd Communication-service
call mvn clean package -DskipTests
cd ..

echo Building evaluation-service...
cd evaluation-service
call mvn clean package -DskipTests
cd ..

echo Building Livraison-service...
cd Livraison-service
call mvn clean package -DskipTests
cd ..

echo Building PaiementAbonnement-service...
cd PaiementAbonnement-service
call mvn clean package -DskipTests
cd ..

echo Building RechercheAnalytics-service...
cd RechercheAnalytics-service
call mvn clean package -DskipTests
cd ..

echo All services built successfully!
echo You can now run: docker-compose up --build
