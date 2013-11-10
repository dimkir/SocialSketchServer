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

    /**
     * These are value for the map. 
     * // maybe we can use them as well as names for the table.
     */
    public static final String FT_ID =  "id";
    public static final String FT_TWEET_ID =  "tweet_id";
    public static final String FT_TWEET =  "tweet";
    public static final String FT_SCREEN_NAME =  "screenname";
    public static final String FT_TIMESTAMP =  "timestamp";
    
    /**
     * This is not a table field
     */
    public static final String F_DATE =  "date";
//    public static final String F_ =  "";
    
    /**
     * Converts element fo map.
     * @return 
     */
    public Map<String, String> toMap() {
        // TODO: implement toMap
        HashMap<String, String> map = new HashMap<String, String>();

        map.put(FT_ID, getId());
        map.put(FT_TWEET_ID, getTweetId());
        map.put(FT_TWEET, getTweet());
        map.put(FT_SCREEN_NAME, getScreenName());
        map.put(FT_TIMESTAMP, getTimeStamp());
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

    public String getScreenName() {
        return screenName;
    }

    public String getTimeStamp() {
        return String.valueOf(timeStamp);
    }

    public TweetRecord setId(int id) {
        this.id = id;
        return this;
    }

    public TweetRecord setTweetId(long tweetId) {
        this.tweetId = tweetId;
        return this;
    }

    public TweetRecord setTweet(String tweet) {
        this.tweet = tweet;
        return this;
    }

    public TweetRecord setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public TweetRecord setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        updateDate(timeStamp);
        return this;
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

    /**
     * Updates date.
     *
     * @param unixepoch
     */
    private void updateDate(long unixepoch) {
        if ( date == null ){
            date = new Date(unixepoch);
        }
        date.setTime(unixepoch);
    }

    @Override
    public String toString() {
        String msg = String.format("Tweet id: %s, Tweet: [%s]", getTweetId(), getTweet());
        
        return msg;
    }
    
    
    
    
    
    
    
    
    
    
}
