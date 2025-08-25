FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /app
COPY . .
CMD ["mvn", "-e", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"]