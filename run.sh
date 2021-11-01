#!/bin/sh

echo "Bygger prosjektet"
./gradlew clean build
echo "Setter opp docker images"
docker compose up -d # docker compose --file docker-compose-aiven.yml up -d
