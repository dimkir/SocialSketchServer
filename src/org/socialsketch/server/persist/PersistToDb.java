package org.socialsketch.server.persist;

/**
 * Class provides interface to persist tweets in abstract manner.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistToDb {
    
    
     
     /**
      * Initializes and autoconnects to the persistent storage.
      */
     public PersistToDb(){
         
     }
     
     
     /**
      * Persists the tweet to DB.
      * 
      * @throws PersistFatalException in case it's the end
      * @throws PersistReconnectableException in case reconnection may help
      */
     public void persistTweet() throws PersistException
     {
     }
     
     /**
      * Reconnects to the persistent storage.
      * 
      */
     public void reconnect(){
     }
     
     /**
      * Creates initial db schema, in case there's no db schema exists.
      */
     private void createSchema(){
     
     
     }
     
}
