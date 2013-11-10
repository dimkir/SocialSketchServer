#!/bin/sh

if [ $# -eq 0 ]
	then
		echo "No arguments supplied, please  specify as artument txt file with terms"
		exit 1
	else
		echo "Received parameter [$1]"

fi

# This script actually runs server
#java -cp bin:lib/jsoup-1.7.2.jar extractcelebrities.micro.ExtractCelebrityCodes
nohup java -cp build/libs/SocialSketchServer.jar:lib/twitter4j-stream-3.0.3.jar:lib/twitter4j-core-3.0.3.jar:lib/log4j-1.2.17.jar:lib/mysql-connector-java-5.1.26-bin.jar  org.socialsketch.server.Application $1 >> bgrunning.log &


