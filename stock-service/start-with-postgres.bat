@echo off
echo ========================================
echo   Service Stock - PostgreSQL
echo ========================================
echo.

echo Configuration:
echo - Base de donnees: PostgreSQL
echo - Database: stock_service
echo - Utilisateur: postgres
echo - Port: 8084
echo.

echo Verifier que PostgreSQL est demarre...
echo Verifier que la base 'stock_service' existe...
echo.

echo Demarrage du service...
echo.

echo URLs disponibles:
echo - Service: http://localhost:8084
echo - API Stocks: http://localhost:8084/api/stocks
echo - API Transactions: http://localhost:8084/api/stock-transactions
echo - API Rapports: http://localhost:8084/api/stock-reports
echo - Eureka: http://localhost:8761
echo.

echo Appuyez sur Ctrl+C pour arreter le service
echo.

mvn spring-boot:run

pause 