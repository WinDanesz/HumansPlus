#!/bin/bash

echo "Setting up environment..."
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java version:"
java -version

echo "Current directory:"
pwd

echo "Gradle files present:"
ls -la gradle*

echo "Attempting gradle build with ForgeGradle 3..."
./gradlew build --stacktrace --info

echo "Build script completed."
