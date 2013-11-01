SocialSketchServer
==================

Server side for SocialSketch toolkit

The main objective for this component is to provide SocialSketchTool with 
source of data (new incoming sketches and so on).

Incuim this should listen for sketches and just retweet them to provided @sketchsocially account.


At the moment I am figuring out how to run this process of listening on my ec2 box.

Also this server is configurable via 
* mysql.properties
* twitter4j.properties
files.

TODO
=====

How to make it possible for the sketch-tool to connect and fetch all "mentions" of tweeted sketches. 
The simpliest approach was to retweet them and then look at the account history. However I am not sure 
how far it is possible to go into the twitter accounts history.


