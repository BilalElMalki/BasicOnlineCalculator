#!/bin/sh
# Maven Wrapper Script
set -e

MAVEN_WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"
DOWNLOAD_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | sed 's/distributionUrl=//')

MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.6"
MVN="$MAVEN_HOME/apache-maven-3.9.6/bin/mvn"

if [ ! -f "$MVN" ]; then
    echo "Downloading Maven 3.9.6..."
    mkdir -p "$MAVEN_HOME"
    TMPZIP="$MAVEN_HOME/maven.zip"
    curl -fsSL "$DOWNLOAD_URL" -o "$TMPZIP"
    unzip -q "$TMPZIP" -d "$MAVEN_HOME"
    rm "$TMPZIP"
    echo "Maven downloaded."
fi

exec "$MVN" "$@"
