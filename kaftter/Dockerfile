FROM openjdk:11-jre

RUN mkdir /kaftter

COPY ./build/libs/*.jar /kaftter/app.jar

RUN adduser --home /kaftter --shell /bin/bash \
    --disabled-password kaftter

USER kaftter

WORKDIR /kaftter

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512", "-Xmx2048"]
