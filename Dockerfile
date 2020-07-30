# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="luizhenrique.se"

# Add a volume pointing to /tmp
VOLUME /tmp

# Expose port
EXPOSE 7000

# The application's jar file
ARG JAR_FILE=build/libs/kotlin-auth-service-1.0.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} kotlin-auth-service.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kotlin-auth-service.jar"]