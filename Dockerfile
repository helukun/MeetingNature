FROM openjdk:19
ADD target/*.jar admin-microservice.jar
EXPOSE 9012
ENTRYPOINT ["java", "-jar", "admin-microservice.jar"]