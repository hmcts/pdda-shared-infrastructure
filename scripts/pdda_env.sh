#!/bin/zsh
#XHIBIT PDDA vars

export JAVA_VERSION="17"
export TOMEE_VERSION="8.0.13"
export PG_VERSION="14"
export SONAR_VERSION="10.0.0.68432"

export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-$JAVA_VERSION.jdk/Contents/Home"
export PDDA_HOME="/Users/$(whoami)/Projects/PDDA/pdda_v2"
export TOMEE_HOME="/opt/homebrew/Cellar/tomee-plume/$TOMEE_VERSION"
export PGDATA="/opt/homebrew/var/postgresql@$PG_VERSION"
export SONAR_HOME="/opt/homebrew/Cellar/sonarqube/$SONAR_VERSION"

export SONAR_DATA="/opt/homebrew/var/sonarqube/data"
export SONAR_LOGS="/opt/homebrew/var/sonarqube/logs"
export SONAR_TEMP="/opt/homebrew/var/sonarqube/temp"
