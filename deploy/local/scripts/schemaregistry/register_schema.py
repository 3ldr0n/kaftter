#!/usr/bin/python3

import os

import requests

schema_registry_url = "http://localhost:8081"
topic = "stream.tweets"
schema_file = "kaftter-consumer/src/main/avro/Tweet.avsc"

aboslute_path_to_schema = os.path.join(os.getcwd(), schema_file)

print("Schema Registry URL: " + schema_registry_url)
print("Topic: " + topic)
print("Schema file: " + schema_file)
print

with open(aboslute_path_to_schema, 'r') as content_file:
    schema = content_file.read()

payload = "{ \"schema\": \"" \
          + schema.replace("\"", "\\\"").replace("\t", "").replace("\n", "") \
          + "\" }"

url = schema_registry_url + "/subjects/" + topic + "-value/versions"
headers = {"Content-Type": "application/vnd.schemaregistry.v1+json"}

r = requests.post(url, headers=headers, data=payload)
if r.status_code == requests.codes.ok:
    print("Success")
else:
    r.raise_for_status()

