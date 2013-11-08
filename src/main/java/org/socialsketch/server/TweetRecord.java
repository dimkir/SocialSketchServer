package org.socialsketch.server;

import java.io.Serializable;
import java.util.Date;

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
    
    
}
