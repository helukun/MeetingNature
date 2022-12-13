FROM openjdk:19
ADD target/*.jar sponsored-microservice.jar
EXPOSE 9010
ENTRYPOINT ["java", "-jar", "sponsored-microservice.jar"]