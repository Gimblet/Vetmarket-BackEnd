REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/usuario:1.0 -f Usuario/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name usuario --network proyectoDAWII -p 6014:6013 proyectoDAWII/usuario:1.0
