@echo off
echo Starting e-commerce application deployment...

echo Step 1: Building all services...
call build-all.bat

echo Step 2: Starting Docker containers...
docker-compose up --build -d

echo Step 3: Waiting for services to start...
timeout /t 30

echo Step 4: Checking service status...
docker-compose ps

echo Deployment completed!
echo Access points:
echo - Eureka Server: http://localhost:8761
echo - API Gateway: http://localhost:8099
echo - PostgreSQL: localhost:5432

echo To stop all services: docker-compose down
echo To view logs: docker-compose logs -f [service-name]
