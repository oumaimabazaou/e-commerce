#!/bin/bash

echo "========================================"
echo "  Demarrage du Service Stock"
echo "========================================"
echo

echo "Compilation du projet..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "ERREUR: La compilation a echoue!"
    exit 1
fi

echo
echo "Demarrage du service sur le port 8084..."
echo
echo "URLs disponibles:"
echo "- Service: http://localhost:8084"
echo "- API Stocks: http://localhost:8084/api/stocks"
echo "- API Transactions: http://localhost:8084/api/stock-transactions"
echo "- API Rapports: http://localhost:8084/api/stock-reports"
echo
echo "Appuyez sur Ctrl+C pour arreter le service"
echo

mvn spring-boot:run 