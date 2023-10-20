FROM maven:3.9.4-eclipse-temurin-21-alpine
ADD target/role-based-auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
