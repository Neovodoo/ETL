# syntax=docker/dockerfile:1.4

# Stage 1: Builder stage using Maven and JDK 17
FROM --platform=$BUILDPLATFORM maven:3.8.5-eclipse-temurin-17 AS builder

# Set working directory for build
WORKDIR /app

# Copy only the necessary files first (pom.xml) to leverage Docker cache
COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline

# Now copy the source code and build the application
COPY src /app/src
RUN mvn clean package -DskipTests

# Stage 2: Production preparation - Extract necessary files for production
FROM builder AS prepare-production
RUN mkdir -p target/dependency
WORKDIR /app/target/dependency
RUN jar -xf ../*.jar

# Stage 3: Final runtime image using minimal JRE
FROM eclipse-temurin:17-jre-focal

# Expose the port for the Spring Boot application
EXPOSE 8080

# Set a volume for temporary files
VOLUME /tmp

# Define dependency path
ARG DEPENDENCY=/app/target/dependency

# Copy only the necessary parts of the build into the runtime image
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "org.example.ConfigServiceApplication"]
