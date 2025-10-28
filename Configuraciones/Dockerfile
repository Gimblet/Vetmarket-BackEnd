FROM maven:3-eclipse-temurin-17 AS Builder

WORKDIR /workspace

COPY Configuraciones/src/ ./src

COPY Configuraciones/pom.xml ./pom.xml

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre

COPY --from=Builder /workspace/target/*.jar config-server.jar

ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]