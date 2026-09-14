FROM eclipse-temurin:21-jre

WORKDIR /app

# Spring Boot application
COPY target/salesforce_integration_service-0.0.1-SNAPSHOT.jar app.jar

# OpenTelemetry Java Agent
COPY opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/otel/opentelemetry-javaagent.jar", "-jar", "/app/app.jar"]