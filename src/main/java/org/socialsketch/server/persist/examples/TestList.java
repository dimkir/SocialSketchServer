package org.socialsketch.server.persist.examples;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import org.socialsketch.server.persist.TweetRecord;

/**
 * Test to try to persist tweets into db, using the PersistToDb API.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class TestList {
   
    /**
     * Just inits the object and tries persisting smth.
     * @param args 
     */
    public static void main(String[] args) {
        try {
            // the question is: where will data source configuration be taken from.
            PersistToDb pers = PersistToDb.getInstance();
            
            List<TweetRecord> latest = pers.getLatestTweets(10);
            
            if ( latest.size() == 0 ){
                System.out.println("*** Current list of latest tweets is empty.");
            }
            for(TweetRecord tr : latest){
                System.out.println("TweetRecord: " + tr.toString());
            }
            
            
        } catch (PersistException ex) {
            Logger.getLogger(TestInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
