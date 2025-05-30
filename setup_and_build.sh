#!/bin/bash

# Build script for HumansPlus mod
echo "Setting up environment..."
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java version:"
java -version

echo "Running gradle setup..."
cd /workspaces/HumansPlus

# Try different gradle approaches
if [ -f "./gradlew" ]; then
    echo "Trying gradlew..."
    ./gradlew setupDecompWorkspace --stacktrace
elif [ -f "./gradle-4.9/bin/gradle" ]; then
    echo "Trying direct gradle..."
    ./gradle-4.9/bin/gradle setupDecompWorkspace --stacktrace
else
    echo "No gradle found!"
    exit 1
fi
