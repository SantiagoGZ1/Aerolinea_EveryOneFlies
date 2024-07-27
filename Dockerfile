FROM openjdk:22-jdk-slim

COPY build/libs/Aerolinea_EveryOneFlies-0.0.1-SNAPSHOT-plain.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]