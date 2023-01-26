#!/usr/bin/env sh
set -e
java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar /usr/local/lib/*.jar
