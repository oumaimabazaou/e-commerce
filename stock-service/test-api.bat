@echo off
echo ========================================
echo   Test des APIs du Service Stock
echo ========================================
echo.

echo Attendre que le service demarre...
timeout /t 10 /nobreak > nul

echo.
echo Test 1: Verifier que le service repond...
curl -s http://localhost:8084/actuator/health
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le service ne repond pas!
    echo Assurez-vous que le service est demarre sur le port 8084
    pause
    exit /b 1
)

echo.
echo Test 2: Lister tous les stocks...
curl -s http://localhost:8084/api/stocks

echo.
echo Test 3: Obtenir les stocks en rupture...
curl -s http://localhost:8084/api/stocks/rupture

echo.
echo Test 4: Compter les transactions par type...
curl -s http://localhost:8084/api/stock-transactions/count/par-type

echo.
echo ========================================
echo   Tests termines!
echo ========================================
echo.
echo URLs disponibles:
echo - Service: http://localhost:8084
echo - Console H2: http://localhost:8084/h2-console
echo - API Stocks: http://localhost:8084/api/stocks
echo - API Transactions: http://localhost:8084/api/stock-transactions
echo - API Rapports: http://localhost:8084/api/stock-reports
echo.

pause 