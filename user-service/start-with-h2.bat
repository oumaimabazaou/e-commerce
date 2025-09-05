@echo off
echo Demarrage du User Service avec H2...
echo.
echo Configuration:
echo - Port: 8087
echo - Base de donnees: H2 (en memoire)
echo - Console H2: http://localhost:8087/h2-console
echo - JDBC URL: jdbc:h2:mem:userdb
echo - Username: sa
echo - Password: (vide)
echo.
echo Demarrage en cours...
echo.
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"
echo.
echo Service arrete.
pause