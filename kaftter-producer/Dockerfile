FROM openjdk:11-jre

RUN mkdir /kaftter-producer

COPY ./build/libs/*.jar /kaftter-producer/app.jar

RUN adduser --home /kaftter-producer --shell /bin/bash \
    --disabled-password kaftter-producer

USER kaftter-producer

WORKDIR /kaftter-producer

ENV TWITTER_CONSUMER_KEY=BbWmv1UqgokQR7raFEd6TeVWs
ENV TWITTER_CONSUMER_SECRET="SM2mVzdVOz3bBGFVd9h9Yob9G8Edf9czPWqqxD7o0CXJEsbjRk"
ENV TWITTER_TOKEN="1147502274215862273-teEdC1ilqUQNGRWG9nRRPmchjH1iDN"
ENV TWITTER_SECRET="xFo6T7MHiCZ1qlsjfBDLVGzDgt0B48Uv41OOzxmI9M7KW"

ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512", "-Xmx1024"]
