# Contruir JAR del proyecto
# - Imagen de contruccion Maven Java 17
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# - Carpeta donde se va a trabajar
WORKDIR /workspace

# -
COPY servicios-comunes ./servicios-comunes
COPY Autenticacion ./Autenticacion

# -
RUN mvn -f servicios-comunes/pom.xml clean install -DskipTests
RUN mvn -f Autenticacion/pom.xml clean install -DskipTests

# Desplegar la aplicacion
# - Imagen ligera para la ejecucion de la aplicacion
FROM eclipse-temurin:17-jre

# - carpeta de trabajo
WORKDIR /app

# - copiar el .jar generado
COPY --from=builder /workspace/Autenticacion/target/*.jar Autenticacion.jar

# -
EXPOSE 6007

# - comando que debe ejecutar cuando arraque la aplicacion
ENTRYPOINT ["java", "-jar", "Autenticacion.jar"]