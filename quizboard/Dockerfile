FROM eclipse-temurin:21-jre-alpine

WORKDIR /opt/app
COPY backend/build/libs/*.jar ./
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
