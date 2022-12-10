FROM openjdk:19
MAINTAINER bingo
ADD target/*.jar user-microservice.jar
EXPOSE 9007
ENTRYPOINT ["java", "-jar", "demo.jar"]