REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/emails:1.0 -f Emails/Dockerfile .

REM --- Ejecución del contenedor ---
docker run -d --name emails --network proyectoDAWII -p 6018:6017 proyectoDAWII/emails:1.0