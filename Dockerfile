FROM openjdk:11-jdk
COPY build/libs/soup-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]