# Auth token generation demo via a WireMock extension

## Requirements
A locally installed Java 17+ JRE/JDK.

## Building and running

### Windows
gradlew assemble
java -cp build\libs\wiremock-standalone-3.13.0.jar:build\libs\wiremock-oss-auth-token-extension-demo-1.0-SNAPSHOT.jar wiremock.Run

### Linux/OSX
./run.sh