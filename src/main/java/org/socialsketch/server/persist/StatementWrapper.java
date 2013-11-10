package org.socialsketch.server.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import twitter4j.Status;

/**
 * This encapsulates creating prepared statement to insert tweet into database.
 * 
 * This is still logically coupled to the table spec (which is in sql-text file inside
 * the resources).
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
//TODO: this class is logically coupled to the table structure. 
// this table structure should be somehow encapsulated into specific class.
class StatementWrapper {
    
    
    private final String mTableName;
    private final Connection mConn;
    
    //TODO: remove somehow dependency on this thing.
    private final String mQueryBase = "INSERT INTO `%s` (`tweet_id`,`tweet`, `screenname`, `timestamp`) VALUES ( ? , ? , ? , ?)";
            
    private String mQueryWithParameters;
    private long mTweetId;
    private String mTweetText;
    private String mScreenName;
    private Date mDate;
    
    
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
        st.setString(3, mScreenName);
        st.setLong(4, mDate.getTime()); // get timestamp since 1970 (unixtime)
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
        mScreenName = status.getUser().getScreenName();
        mDate = status.getCreatedAt();
    }
    
    
    
}
