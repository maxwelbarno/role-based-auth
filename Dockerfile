FROM eclipse-temurin:21-alpine
ARG JWT_SECRET=daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb
ENV JWT_SECRET=$JWT_SECRET
ADD target/role-based-auth-v1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "---application.security.jwt.secret-key=$JWT_SECRET"]
