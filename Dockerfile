FROM openjdk:19
ADD target/*.jar follow-microservice.jar
EXPOSE 9008
ENTRYPOINT ["java", "-jar", "follow-microservice.jar"]