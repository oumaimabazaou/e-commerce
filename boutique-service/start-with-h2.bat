@echo off
echo Demarrage du Boutique Service avec H2...
echo.
echo Configuration:
echo - Port: 8088
echo - Base de donnees: H2 (en memoire)
echo - Console H2: http://localhost:8088/h2-console
echo - JDBC URL: jdbc:h2:mem:boutiquedb
echo - Username: sa
echo - Password: (vide)
echo.
echo Demarrage en cours...
echo.
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"
echo.
echo Service arrete.
pause