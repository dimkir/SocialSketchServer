package org.socialsketch.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.TwitterException;

/**
 * This is entry point to server side of the social sketch.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            System.out.println("Running social sketch server test stub");
            PrintFilterStream.main(new String[] {});
            
            
            
            
        } catch (TwitterException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
