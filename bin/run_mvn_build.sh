#!/bin/bash

JAVA_ROOT=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64
export M2_HOME=/tmp/apache-maven-3.2.5/
export M2=$M2_HOME/bin
export JAVA_HOME=$JAVA_ROOT
export PATH=$PATH:$M2:$JAVA_HOME/bin

# Run the build
echo -e "\n### Running mvn clean package"
mvn clean package
