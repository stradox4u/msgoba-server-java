FROM maven:3.9.11-eclipse-temurin-24 AS development
WORKDIR /app
COPY . .
CMD ["mvn", "-e", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"]


FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/data/*.csv /app/data/
EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]