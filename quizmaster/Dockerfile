FROM eclipse-temurin:21-jre-alpine

WORKDIR /opt/app
COPY build/libs/*.jar ./
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
