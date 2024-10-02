FROM maven:3.8.3-openjdk-17

COPY target/*.jar desafio.jar

ENTRYPOINT ["java", "-jar", "desafio.jar"]