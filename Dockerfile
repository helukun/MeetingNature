FROM openjdk:19
ADD target/*.jar complaint-microservice.jar
EXPOSE 9013
ENTRYPOINT ["java", "-jar", "complaint-microservice.jar"]