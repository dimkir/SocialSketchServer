package org.socialsketch.server.persist;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This is domain object: the tweet record.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class TweetRecord implements Serializable
{
    private int id;
    private long tweetId;
    private String tweet;
    private String screenName;
    private long timeStamp;
    private Date date; // converted from timestamp.

    
    public static final String F_ID =  "id";
    public static final String F_TWEET_ID =  "tweetId";
    public static final String F_TWEET =  "tweet";
    public static final String F_SCREEN_NAME =  "screenName";
    public static final String F_TIMESTAMP =  "timeStamp";
    public static final String F_DATE =  "date";
//    public static final String F_ =  "";
    
    /**
     * Converts element fo map.
     * @return 
     */
    public Map<String, String> toMap() {
        // TODO: implement toMap
        HashMap<String, String> map = new HashMap<String, String>();

        map.put(F_ID, getId());
        map.put(F_TWEET_ID, getTweetId());
        map.put(F_TWEET, getTweet());
        map.put(F_SCREEN_NAME, getScreenName());
        map.put(F_TIMESTAMP, getTimeStamp());
        map.put(F_DATE, getDate());
        return map;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getTweetId() {
        return String.valueOf(tweetId);
    }

    public String getTweet() {
        return tweet;
    }

    private String getScreenName() {
        return screenName;
    }

    private String getTimeStamp() {
        return String.valueOf(timeStamp);
    }

    
    /**
     * Returns date as a string.
     * 
     * @return 
     */
    private String getDate() {
        // TODO: make it render to string of the format we want. not just random sring.
        return date.toString();
    }
    
    
    
    
    
    
    
    
    
    
}
