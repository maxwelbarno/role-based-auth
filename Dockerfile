FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN mvn -B -f pom.xml clean package -DskipTests

FROM maven:3.9.4-eclipse-temurin-21-alpine
COPY --from=build /app/target/role-based-auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
