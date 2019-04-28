FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/discovery-service.jar discovery-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","/discovery-service.jar"]
EXPOSE 8671
