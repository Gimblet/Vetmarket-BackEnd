REM --- Construcción de la imagen Docker ---
docker build -t proyectoDAWII/ordencompra:1.0 -f OrdenCompra/Dockerfile .
REM Crea una imagen llamada 'proyectoDAWII/ordencompra' con la versión 1.0
REM -t = etiqueta de la imagen
REM -f = indica el archivo Dockerfile a usar
REM . = significa que está en la carpeta actual

REM --- Ejecución del contenedor ---
docker run -d --name ordencompra --network proyectoDAWII -p 6001:6000 proyectoDAWII/ordencompra:1.0
REM -d = ejecuta en segundo plano (modo detached)
REM --name = nombre del contenedor
REM --network = conecta el contenedor a una red Docker (para que se comunique con otros servicios)
REM -p 5000:5000 = mapea el puerto del contenedor al del host
REM proyectoDAWII/ordencompra:1.0 = nombre de la imagen que se ejecutará
