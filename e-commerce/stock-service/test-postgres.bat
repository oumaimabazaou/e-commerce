@echo off
echo ========================================
echo   Test Configuration PostgreSQL
echo ========================================
echo.

echo 1. Verifier que PostgreSQL est demarre...
echo 2. Verifier que la base 'stock_service' existe...
echo 3. Demarrer le service...
echo.

echo Configuration:
echo - Database: stock_service
echo - Utilisateur: postgres
echo - Mot de passe: 123456789
echo - Port: 8084
echo.

echo Appuyez sur une touche pour demarrer le service...
pause

echo.
echo Demarrage du service Stock avec PostgreSQL...
echo.

mvn spring-boot:run

pause 