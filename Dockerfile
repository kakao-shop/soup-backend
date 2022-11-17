FROM openjdk:11-jdk
COPY build/libs/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]