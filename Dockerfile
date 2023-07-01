FROM openjdk:17-jdk-alpine
MAINTAINER AliRanjbari
COPY target/Baloot.jar baloot-server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/baloot-server.jar"]