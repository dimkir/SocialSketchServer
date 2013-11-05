package org.socialsketch.server.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import twitter4j.Status;

/**
 * This encapsulates creating prepared statement to insert tweet into database.
 * 
 * This is still logically coupled to the table spec (which is in sql-text file inside
 * the resources).
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
class StatementWrapper {
    
    
    private final String mTableName;
    private final Connection mConn;
    
    private final String mQueryBase = "INSERT INTO `%s` (`tweet_id`,`tweet`) VALUES ( ? , ? )";
            
    private String mQueryWithParameters;
    private long mTweetId;
    private String mTweetText;
    
    
    /**
     * 
     * @param tableName not null valid (for mysql) table name.
     * @param conn 
     * 
     */
    StatementWrapper(String tableName, Connection conn){
        if ( tableName == null){ throw new NullPointerException("table name cannot be null"); }
        if ( conn == null){ throw new NullPointerException("Connection cannot be null"); }

        mTableName = tableName;
        mQueryWithParameters = String.format(mQueryBase, tableName);
        mConn = conn;
    }
    

//    @Override
    public int executeUpdate() throws SQLException {
        PreparedStatement st = mConn.prepareStatement(mQueryWithParameters);
        st.setLong(1, mTweetId);
        st.setString(2, mTweetText);
        return st.executeUpdate();
    }

    /**
     * Sets tweet info as parameters for query.
     * 
     * @param status 
     */
    public void setTweet(Status status) {
        mTweetId = status.getId();
        mTweetText = status.getText();
    }
    
    
    
}
