# Stage 1: Build the application
# Use a valid Maven image with JDK 17
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Make the Maven wrapper script executable
RUN chmod +x ./mvnw

# Build the application, skipping tests to speed up the build
RUN ./mvnw package -DskipTests


# Stage 2: Create the final production image
# Use a lightweight JDK 17 image for the final image
FROM openjdk:17-slim
WORKDIR /app

# Copy the built JAR from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]