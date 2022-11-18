FROM openjdk:11-jdk as Builder
MAINTAINER YejiKim <2214yjgm@gmail.com>

COPY ./gradlew .
COPY ./gradle .
COPY ./build.gradle .
COPY ./settings.gradle .
#RUN chmod +x ./gradlew -x test
#RUN ./gradlew build

FROM openjdk:11-jdk
COPY build/libs/soup-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]