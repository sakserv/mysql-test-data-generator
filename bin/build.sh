#!/bin/bash

JAVA_ROOT=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64

# Install Maven
echo -e "\n#### Installing Maven"
cd /tmp
wget -N http://www.gtlib.gatech.edu/pub/apache/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.tar.gz
tar -xzvf apache-maven-3.2.5-bin.tar.gz
export M2_HOME=/tmp/apache-maven-3.2.5/
export M2=$M2_HOME/bin
export JAVA_HOME=$JAVA_ROOT
export PATH=$PATH:$M2:$JAVA_HOME/bin
mvn --version

# Run the build
echo -e "\n### Running mvn clean package"
cd /tmp/mysql-test-data-generator && mvn clean package
