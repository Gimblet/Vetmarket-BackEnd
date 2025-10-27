REM --- Construcción de la imagen Docker ---
docker build -f Producto/Dockerfile . -t producto:1.0

REM --- Ejecución del contenedor ---
docker run -d --name producto --network proyectoDAWII-net -p 6016:6015 producto:1.0
