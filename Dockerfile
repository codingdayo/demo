FROM openjdk:17-jdk-alpine AS builder

#n
WORKDIR /app

COPY pom.xml .
RUN mvn clean package

FROM openjdk:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]