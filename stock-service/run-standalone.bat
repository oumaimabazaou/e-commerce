@echo off
echo ========================================
echo   Service Stock - Mode Standalone
echo ========================================
echo.

echo Demarrage du service sur le port 8084...
echo Base de donnees: H2 en memoire
echo.
echo URLs disponibles:
echo - Service: http://localhost:8084
echo - Console H2: http://localhost:8084/h2-console
echo - API Stocks: http://localhost:8084/api/stocks
echo - API Transactions: http://localhost:8084/api/stock-transactions
echo - API Rapports: http://localhost:8084/api/stock-reports
echo.
echo Appuyez sur Ctrl+C pour arreter le service
echo.

mvn spring-boot:run -Dspring-boot.run.profiles=standalone

pause 