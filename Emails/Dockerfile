# Contruir JAR del proyecto
# - Imagen de contruccion Maven Java 17
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# - Carpeta donde se va a trabajar
WORKDIR /workspace

# -
COPY servicios-comunes ./servicios-comunes
COPY Emails ./Emails

# -
RUN mvn -f servicios-comunes/pom.xml clean install -DskipTests
RUN mvn -f Emails/pom.xml clean install -DskipTests

# Desplegar la aplicacion
# - Imagen ligera para la ejecucion de la aplicacion
FROM eclipse-temurin:17-jre

# - carpeta de trabajo
WORKDIR /app

# - Especificar el perfil
ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_CONFIG_LOCATION=classpath:/application-docker.yml

# - copiar el .jar generado
COPY --from=builder /workspace/Emails/target/*.jar Emails.jar

# -
EXPOSE 6017

# - comando que debe ejecutar cuando arranque la aplicacion
ENTRYPOINT ["java", "-jar", "Emails.jar"]