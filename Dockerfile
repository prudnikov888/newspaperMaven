# --- Build stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy poms first so dependency resolution is cached across source-only changes
COPY pom.xml .
COPY dao/pom.xml dao/pom.xml
COPY services/pom.xml services/pom.xml
COPY web/pom.xml web/pom.xml
RUN mvn -B -q -pl dao,services,web -am dependency:go-offline

COPY dao dao
COPY services services
COPY web web
RUN mvn -B -q -pl web -am clean package -DskipTests

# --- Runtime stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/web/target/web.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
