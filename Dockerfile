FROM openjdk:19
ADD target/*.jar audit-microservice.jar
EXPOSE 9011
ENTRYPOINT ["java", "-jar", "audit-microservice.jar"]