REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/servicio:1.0 -f Servicio/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name servicio --network proyectoDAWII -p 6012:6011 proyectoDAWII/servicio:1.0