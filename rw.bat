
@REM This script actually runs server
@REM #java -cp bin:lib/jsoup-1.7.2.jar extractcelebrities.micro.ExtractCelebrityCodes
@REM java -cp build/libs/SocialSketchServer.jar;lib/twitter4j-stream-3.0.3.jar;lib/twitter4j-core-3.0.3.jar;lib/log4j-1.2.17.jar;lib/mysql-connector-java-5.1.26-bin.jar  org.socialsketch.server.Main %1

@java -jar build/libs/socialsketchserver-0.1.0.jar %1



