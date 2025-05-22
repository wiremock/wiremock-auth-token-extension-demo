#!/usr/bin/env bash

./gradlew assemble
java -cp build/libs/wiremock-standalone-3.13.0.jar:build/libs/wiremock-oss-auth-token-extension-demo-1.0-SNAPSHOT.jar wiremock.Run