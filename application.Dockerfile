FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/colaboratory_assignment-1.0.0-all.jar /app/myapp.jar
ENTRYPOINT ["java","-jar","/app/myapp.jar"]