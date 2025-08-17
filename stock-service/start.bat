@echo off
echo ========================================
echo   Demarrage du Service Stock
echo ========================================
echo.

echo Compilation du projet...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La compilation a echoue!
    pause
    exit /b 1
)

echo.
echo Demarrage du service sur le port 8084...
echo Configuration: PostgreSQL + Eureka
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

call mvn spring-boot:run

pause 