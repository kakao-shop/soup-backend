FROM openjdk:11-jdk
CMD gradlew build -x test
COPY build/libs/soup-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]