FROM openjdk:11-jre

RUN mkdir /kaftter-api

COPY ./build/libs/*.jar /kaftter-api/app.jar

WORKDIR /kaftter-api

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512", "-Xmx2048"]
