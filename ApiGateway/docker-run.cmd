REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/apigateway:1.0 -f ApiGateway/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name apigateway --network proyectoDAWII -p 8081:8080 proyectoDAWII/apigateway:1.0
