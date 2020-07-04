#!/bin/sh

SCHEMA=$(gawk '{ gsub(/"/, "\\\"") } 1' kaftter/src/main/avro/Tweet.avsc | sed ':a;N;$!ba;s/\n//g' | sed 's/ //g')

PAYLOAD=$(echo "{\"schema\": \"$SCHEMA\"}")

URL="http://localhost:8081/subjects/stream.tweets-value/versions"
HEADERS=""

echo $PAYLOAD

curl -iX POST $URL -H 'Content-Type: application/vnd.schemaregistry.v1+json' \
    -d \'$PAYLOAD\'
