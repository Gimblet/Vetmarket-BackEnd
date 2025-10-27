FROM maven:3-eclipse-temurin-17 AS Builder

WORKDIR /workspace

COPY servicios-comunes ./servicios-comunes
COPY Producto ./Producto

RUN mvn -f servicios-comunes/pom.xml clean install -DskipTests
RUN mvn -f Producto/pom.xml clean install -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=Builder /workspace/Producto/target/*.jar producto-service.jar

ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 6015

ENTRYPOINT ["java", "-jar", "producto-service.jar"]
