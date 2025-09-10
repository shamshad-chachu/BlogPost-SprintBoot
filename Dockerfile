# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the Maven wrapper files and the pom.xml
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Make the Maven wrapper script executable
RUN chmod +x ./mvnw

# Copy the rest of the source code
COPY src ./src

# Build the application, skipping tests to speed up the build
RUN ./mvnw package -DskipTests



# Stage 2: Create the final production image
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/BlogPost-0.0.1-SNAPSHOT.jar BlogPost-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "BlogPost-0.0.1-SNAPSHOT.jar"]