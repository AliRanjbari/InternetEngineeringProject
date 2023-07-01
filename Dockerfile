FROM openjdk:8-jdk-alpine
MAINTAINER AliRanjbari
COPY target/baloot.jar message-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]