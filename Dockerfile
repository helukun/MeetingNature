FROM openjdk:19
ADD target/*.jar user-microservice.jar
EXPOSE 9007
ENTRYPOINT ["java", "-jar", "user-microservice.jar"]