

#
#FROM openjdk:21
#EXPOSE 8080
#ADD target/spring-boot-docker.jar spring-boot-docker.jar
#ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]


FROM openjdk:21
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]

