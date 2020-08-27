FROM openjdk:11-jre

RUN mkdir /kaftter-api

COPY ./build/libs/*.jar /kaftter-api/app.jar

RUN adduser --home /kaftter-api --shell /bin/bash \
    --disabled-password kaftter-api

USER kaftter-api

WORKDIR /kaftter-api

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512", "-Xmx2048"]
