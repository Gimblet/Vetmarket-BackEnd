REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/config-server:1.0 -f Configuraciones/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name config-server --network proyectoDAWII -p 8889:8888 proyectoDAWII/config-server:1.0
