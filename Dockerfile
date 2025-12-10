FROM ghcr.io/dheerajbasude-dev/payroll-management-system-deps:latest AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY mvnw mvnw
COPY .mvn .mvn

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
