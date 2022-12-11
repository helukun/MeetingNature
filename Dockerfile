FROM openjdk:19
ADD target/*.jar process-management-microservice.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "process-management-microservice.jar"]