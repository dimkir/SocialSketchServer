package org.socialsketch.server.persist.examples;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * Test to try to insert data into the table.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class TestInsert {
   
    /**
     * Just inits the object and tries persisting smth.
     * @param args 
     */
    public static void main(String[] args) {
        try {
            // the question is: where will data source configuration be taken from.
            PersistToDb pers = new PersistToDb();
            
            Status tweet = new MyStatus(); // this is "mock" implementation of the twitter Status object.
            
            pers.persistTweet(tweet);
            
            
            
            
        } catch (PersistException ex) {
            Logger.getLogger(TestInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
