#FROM ubuntu:latest
#LABEL authors="dayoawoniyi"
#
#ENTRYPOINT ["top", "-b"]


FROM openjdk:17-slim

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]