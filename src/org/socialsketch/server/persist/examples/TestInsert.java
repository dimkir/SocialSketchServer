package org.socialsketch.server.persist.examples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import twitter4j.Status;

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
