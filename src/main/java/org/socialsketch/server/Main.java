package org.socialsketch.server;

import java.util.ArrayList;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * This is entry point to server side of the social sketch.
 * 
 * IMPORTANT!!!!!!
 * IMPORTANT!!!!!!
 * IMPORTANT!!!!!!
 * IMPORTANT!!!!!!
 * IMPORTANT!!!!!!
 * You need twitter4j.properties file in the root of your sources folder for this to work
 * as application takes keys from there.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            System.out.println("Running social sketch server test stub");
            startListener();
    }    
    
    private static boolean isNumericalArgument(String argument) {
        String args[] = argument.split(",");
        boolean isNumericalArgument = true;
        for (String arg : args) {
            try {
                Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                isNumericalArgument = false;
                break;
            }
        }
        return isNumericalArgument;
    }

    private static void startListener() {

        StatusListener listener = new MyStatusListener();
                

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        
        ArrayList<Long> follow = new ArrayList<Long>();
        ArrayList<String> track = new ArrayList<String>();
        
//        for (String arg : args) {
//            if (isNumericalArgument(arg)) {
//                for (String id : arg.split(",")) {
//                    follow.add(Long.parseLong(id));
//                }
//            } else {
//                track.addAll(Arrays.asList(arg.split(",")));
//            }
//        }
        track.add("@dublinbusnews");
//        track.add("void setup draw");
//        track.add("void size");
        
        long[] followArray = new long[follow.size()];
        for (int i = 0; i < follow.size(); i++) {
            followArray[i] = follow.get(i);
        }
        
        String[] trackArray = track.toArray(new String[track.size()]);

        // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(new FilterQuery(0, followArray, trackArray));        
    }    
    
}
