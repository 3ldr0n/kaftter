# Kaftter

[![Build Status](https://travis-ci.com/eaneto/kaftter.svg?branch=master)](https://travis-ci.com/eaneto/kaftter)

An overengineered architecture to produce a summary about the tweets
of some users. The idea is to produce tweet events of some hashtags.
These events are consumed, saved in Cassandra and summarized by
a spark job.

This really is much more complicated than it should be, but the
goal *is not* to make something simple, it's actually just made to
learn some technologies.

## Architecture

![Architecture](./docs/kaftter.png)

## Local environment

To run all "dependencies" for the services (zookeeper, kafka, schema
registry and cassandra).

```bash
docker-compose -f docker-compose-dependencies.ymL up -d
./deploy/local/scriprts/schemaregistry/register_schema.py
```

### Running all services with docker compose (WIP)

Communication between the containers isn't working yet.

```bash
cd kaftter-producer
./gradlew clean build

cd kaftter-consumer
./gradlew clean build

cd kaftter-api
./gradlew clean build

docker-compose up -d
./deploy/local/scriprts/schemaregistry/register_schema.py
```
