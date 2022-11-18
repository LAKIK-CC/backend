FROM maven:3.8.6-eclipse-temurin-11-alpine AS build
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests

FROM eclipse-temurin:11
WORKDIR /app
COPY --from=build /build/target/lakik-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","/app/lakik-0.0.1-SNAPSHOT.jar"]