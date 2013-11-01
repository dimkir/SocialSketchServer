
@REM This script actually runs server
#java -cp bin:lib/jsoup-1.7.2.jar extractcelebrities.micro.ExtractCelebrityCodes
java -cp dist/SocialSketchServer.jar;lib/twitter4j-stream-3.0.3.jar;lib/twitter4j-core-3.0.3.jar;lib/log4j-1.2.17.jar  org.socialsketch.server.Main


