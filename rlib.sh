#!/bin/sh
# This script actually runs server
# this script is specifying libraries in the build/output/libs directory

#java -cp bin:lib/jsoup-1.7.2.jar extractcelebrities.micro.ExtractCelebrityCodes
java -cp build/libs/SocialSketchServer.jar:build/output/libs/twitter4j-stream-3.0.5.jar:\
build/output/libs/twitter4j-core-3.0.5.jar:build/output/libs/mysql-connector-java-5.1.26.jar:\
build/output/libs/log4j-1.2.17.jar  org.socialsketch.server.Main $1


