FROM openjdk:17

VOLUME /app

COPY target/agil-it-crm-backend-0.0.1-SNAPSHOT.jar /app/agil-it-crm-backend.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/agil-it-crm-backend.jar"]
