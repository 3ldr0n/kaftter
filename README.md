# Kaftter

[![Build Status](https://travis-ci.com/3ldr0n/kaftter.svg?branch=master)](https://travis-ci.com/3ldr0n/kaftter)

Takes data from Twitter and saves it in cassandra.

## Architecture

![Architecture](./docs/kaftter.png)

## Local environment

```bash
docker-compose up -d
```

```bash
./register_schema.py http://localhost:8081 stream.tweets kaftter/src/main/avro/Tweet.avsc
```

### Kaftter producer

```bash
cd kaftter-producer
./gradlew clean build
docker build -t kaftter/kaftter-producer:1.0 .
docker run -d --name kaftter_producer kaftter/kaftter-producer:1.0
```

### Kaftter

```bash
cd kaftter
./gradlew clean build
docker build -t kaftter/kaftter:1.0 .
docker run -d --name kaftter kaftter/kaftter:1.0
```

### Kaftter API

```bash
cd kaftter-api
./gradlew clean build
docker build -t kaftter/kaftter-api:1.0 .
docker run -d --name kaftter-api kaftter/kaftter-api:1.0
```
