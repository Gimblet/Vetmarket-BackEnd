# --- ETAPA 1: Construcción del proyecto ---
FROM maven:3.9.9-eclipse-temurin-17 AS builder
# Imagen Maven + Java 17 para compilar el código

WORKDIR /workspace
# Carpeta de trabajo dentro del contenedor

COPY servicios-comunes ./servicios-comunes
COPY Usuario ./Usuario
# Copiamos el codigo fuete y su dependencia local

RUN mvn -f servicios-comunes/pom.xml clean install -DskipTests
RUN mvn -f Usuario/pom.xml clean install -DskipTests
# Compila el proyecto y genera el .jar

# --- ETAPA 2: Ejecución del proyecto ---
FROM eclipse-temurin:17-jre
# Imagen ligera de Java 17 (solo JRE)

WORKDIR /app
# Carpeta de trabajo donde irá el .jar

ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_CONFIG_LOCATION=classpath:/application-docker.yml
#Especificar cual perfil leer

COPY --from=builder /workspace/Usuario/target/*.jar Usuario.jar
# Copiamos el .jar generado

EXPOSE 6013

ENTRYPOINT ["java", "-jar", "Usuario.jar"]
# Comando que arranca la app
