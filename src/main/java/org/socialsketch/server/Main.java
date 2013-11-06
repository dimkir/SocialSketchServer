package org.socialsketch.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * Starts server side, which is listening to the twitter keywords, persists given tweets,
 * and as well performs action upon selection of the tweet.
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

    private static final Logger logger = org.apache.log4j.Logger.getLogger(Main.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        printCommandLineParams(args);
        if ( args == null || args.length == 0){
            System.out.println("Please specify one parameter: termfile.txt");
            return;
        }
        
        try {
            System.out.printf("Attempting to read listening configuration from file [%s]...\n", args[0]);
            List<String> listOfTerms = readStrings(args[0]);
            System.out.printf("Read from file %d lines.", listOfItems.size() );
            
            PersistToDb persistor = new PersistToDb();
            startListener(persistor, listOfTerms, new IOnStatusReceived() {

                @Override
                public void onStatusReceived(Twitter twitter, Status status) {
                    try {
                        twitter.createFavorite(status.getId());
                        
                    } catch (TwitterException ex) {
                        logger.warn("Cannot favorite tweet with id: " + status.getId() , ex);
                    }

                }
            });
            
            
        } catch (PersistException ex) {
            System.out.println("There was an error initializing persistor: " + ex.getMessage());
            //ex.printStackTrace();
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Aborting. There was an error whilst reading file with configuration: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }    
    
    /**
     * Determines if argument is numerical. (what _exactly_ does it mean?)
     * 
     * @param argument
     * @return 
     */
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

    /**
     * Initializes persisting listener.
     * 
     * @param persistor 
     * @param track NON NULL, NON EMPTY, list of keywords(terms) to track.
     * @param callback to be called upon every new status received.
     */
    private static void startListener(PersistToDb persistor, List<String> track, IOnStatusReceived callback) {
        if ( track == null ){ throw new IllegalArgumentException("List of words to track cannot be NULL");   }
        if ( track.isEmpty() ){ throw new IllegalArgumentException("List of words to track cannot be empty"); }

        Twitter twitter = TwitterFactory.getSingleton();
        
        StatusListener listener = new MyStatusListener(persistor, twitter, callback);
                

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        
        /**
         * This looks like array of the user ID's we're going to listen to 
         * (if we want to listen to specific users).
         */
        ArrayList<Long> follow = new ArrayList<Long>();
        
        /**
         * This looks like array of "keywords" we may want to track.
         */
      //  ArrayList<String> track = new ArrayList<String>();
        
//        for (String arg : args) {
//            if (isNumericalArgument(arg)) {
//                for (String id : arg.split(",")) {
//                    follow.add(Long.parseLong(id));
//                }
//            } else {
//                track.addAll(Arrays.asList(arg.split(",")));
//            }
//        }
        //track.add("@dublinbusnews");
//        track.add("void setup draw");
//        track.add("void size");
      //  track.add("#websummit");
        
        long[] followArray = new long[follow.size()];
        for (int i = 0; i < follow.size(); i++) {
            followArray[i] = follow.get(i);
        }
        
        String[] trackArray = track.toArray(new String[track.size()]);

        // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(new FilterQuery(0, followArray, trackArray));        
    }    

    /**
     * Prints command line params.
     * 
     * @param args 
     */
    private static void printCommandLineParams(String[] args) {
        if ( args == null ){
            System.out.println(">>No command line parameters specified (NULL)");
            return;
        }

        if (  args.length == 0  ){
            System.out.println(">>No command line parameters specified (0 length)");
            return;
        }
        
        for(int i = 0 ; i < args.length ; i++){
            
            System.out.printf("Parameter %d equal [%s]\n", i, args[i]);
        }
    }

    
    /**
     * Reads amount of lines from file into memory.
     * 
     * @param filePath NON NULL obviously.
     * @param maxLines -1 for no limit, valid values are positive integers. 
     * @throws IllegalArgumentException in case maxLines parameter is invalid.
     * @throws IOException in case there's a problem when reading/finding file.
     * 
     * @return list of lines.
     */
    private static List<String> readStrings(String filePath, int maxLines )  throws IOException
    {
        if ( maxLines == 0 || maxLines <= -2){
            throw new IllegalArgumentException("maxLines parameter can only be -1 or positive number, supplied(" + maxLines +")");
        }
        
        FileInputStream fis = null;
        InputStreamReader isr  = null;
        BufferedReader breader  = null; 
        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            breader = new BufferedReader(isr);

            String line;
            int linesCompleted = 0;
            List<String> list = new ArrayList<>();
            while (  ((linesCompleted < maxLines) || ( maxLines == -1)) &&
                    (null != ( line = breader.readLine() )  )
                    ) 
            {
                list.add(line);
                linesCompleted++;
            }
            return list;
        } finally {
            if ( breader != null ){
                try {
                    breader.close();
                } catch (IOException ex) {
                    logger.warn("Cannot close resource breader", ex);
                }
            }
            
            if ( isr != null ){
                try {
                    isr.close();
                } catch (IOException ex) {
                    logger.warn("Cannot closer resource isr", ex);
                }
            }
            
            if ( fis != null  ){
                try {
                    fis.close();
                } catch (IOException ex) {
                    logger.warn("Cannot close resource fis", ex);
                }
            }

        }

        
    }
    
    
    /**
     * Reads txt file lines into memory.
     * 
     * @param filePath
     * @return 
     */
    private static List<String> readStrings(String filePath) throws IOException {
        return readStrings(filePath, -1);
    }
    
}
