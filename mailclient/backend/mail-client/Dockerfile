FROM openjdk:8-jdk-alpine
VOLUME /tmp
# Add the application's jar to the container
COPY /*.jar mail-client.jar

# openjdk:8-jdk-alpine'da bash yok sh var. docker-compose.yml'da CMD'de bash'a ihtiyacımız var.
RUN apk add --no-cache bash

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=file:/config/mail-client-config.yml", "/mail-client.jar"]
