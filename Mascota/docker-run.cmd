REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/mascota:1.0 -f Mascota/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name mascota --network proyectoDAWII-net -p 6010:6009 proyectoDAWII/mascota:1.0
