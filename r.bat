
@REM This script actually runs server
@REM #java -cp bin:lib/jsoup-1.7.2.jar extractcelebrities.micro.ExtractCelebrityCodes
@REM java -cp build/libs/SocialSketchServer.jar;lib/twitter4j-stream-3.0.3.jar;lib/twitter4j-core-3.0.3.jar;lib/log4j-1.2.17.jar;lib/mysql-connector-java-5.1.26-bin.jar  org.socialsketch.server.Main %1

@java -cp build/libs/SocialSketchServer.jar;build/output/libs/twitter4j-core-3.0.5.jar;build/output/libs/twitter4j-stream-3.0.5.jar;build/output/libs/mysql-connector-java-5.1.26.jar;build/output/libs/log4j-1.2.17.jar  org.socialsketch.server.Main %1



