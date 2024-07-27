FROM openjdk:22-jdk-slim

ADD https://wetransfer.com/downloads/d8bb5dde95b93878861c006737a5709b20240727155747/5292ddf7831fea56eb3b825e812cc02d20240727155807/ef228b build/libs/Aerolinea_EveryOneFlies-0.0.1-SNAPSHOT.jar

COPY build/libs/Aerolinea_EveryOneFlies-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]