FROM openjdk:19
ADD target/*.jar sponsor-microservice.jar
EXPOSE 9009
ENTRYPOINT ["java", "-jar", "sponsor-microservice.jar"]