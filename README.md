# Kaftter

[![Build Status](https://travis-ci.com/3ldr0n/kaftter.svg?branch=master)](https://travis-ci.com/3ldr0n/kaftter)

[Sonar report](https://sonarcloud.io/dashboard?id=3ldr0n_kaftter)

Takes data from Twitter and saves it in cassandra.

## Local environment

```bash
docker-compose up -d
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

## Endpoints

### Get tweets

```bash
GET /api/tweets/{numberOfTweets}
```

#### Response payload example

```json
{
  "key": "value"
}
```

### Export tweets to csv

```bash
GET /api/tweets/export/{numberOfTweets}
```
