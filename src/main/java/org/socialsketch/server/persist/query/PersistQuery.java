package org.socialsketch.server.persist.query;

import twitter4j.Status;

/**
 * This is convenience class which is building query for persisting of the 
 * tweet.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistQuery implements IQuery
{

    /**
     * Initializes query builder for persist query, takes name of the destination table
     * as parameter.
     * 
     * <p>
     * This class encapsulates which data off the tweet is taken and how it is inserted.
     * 
     * <p>
     * Also this PersistQuery is coupled to the table format (spec).
     * 
     * @param tweetTableName destination table where the tweet will be persisted.
     */
    public PersistQuery(String tweetTableName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sets query parameters from the tweet which was passed.
     * 
     * @param tweet NON-NULL, tweet which needs to be persisted.
     * @return 
     */
    public PersistQuery setTweet(Status tweet){
        //TODO: implememtn
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String renderToString() {
        // TODO: implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
