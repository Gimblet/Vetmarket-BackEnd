
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /workspace

COPY Eureka/src ./src
COPY Eureka/pom.xml ./pom.xml

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=docker

COPY --from=builder /workspace/target/*.jar Eureka.jar

EXPOSE 8761

ENTRYPOINT [ "java","-jar","Eureka.jar" ]