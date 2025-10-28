REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/autenticacion:1.0 -f Autenticacion/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name autenticacion --network proyectoDAWII -p 6008:6007 proyectoDAWII/autenticacion:1.0
