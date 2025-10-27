REM --- Construcción de la imagen Docker ---
docker build -f Configuraciones/Dockerfile . -t config-server:1.0

REM --- Ejecución del contenedor ---
docker run -d --name config-server --network proyectoDAWII-net -p 8889:8888 config-server:1.0
