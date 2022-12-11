FROM openjdk:19
ADD target/*.jar project-microservice.jar
EXPOSE 9006
ENTRYPOINT ["java", "-jar", "project-microservice.jar"]