REM --- Construcción de la imagen Docker ---
docker build -f Configuraciones/Dockerfile . -t config-server:1.0

REM --- Ejecución del contenedor ---
docker run -d --name config-server --network proyectoDAWII-net -p 6001:6000 config-server:1.0
