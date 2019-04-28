FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} discovery-server.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","/discovery-server.jar"]
EXPOSE 8671
