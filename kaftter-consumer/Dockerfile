FROM openjdk:11-jre

RUN mkdir /kaftter-consumer

COPY ./build/libs/*.jar /kaftter-consumer/app.jar

WORKDIR /kaftter-consumer

EXPOSE 8079

ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512", "-Xmx2048"]
