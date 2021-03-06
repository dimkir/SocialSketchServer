package org.socialsketch.server.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import twitter4j.Status;

/**
 * Class provides interface to persist tweets in abstract manner.
 * 
 * <p>
 * As data source is using mysql (through JDBC Connector/J driver).
 * 
 * <p>
 * To configure mysql, the class uses {@link MySqlProperties#MySqlProperties() } 
 * default constructor. Incuim, it is reading "mysql.properties" from the default package.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistToDb {
    
    
    private static PersistToDb mInstance = null;
    
    private final JdbcTemplate mJdbcTemplate;
    
    /**
     * Returns instance (singleton style).
     * 
     * @return instance of the PersistToDb
     * @throws PersistException when there's an error initializing persistor.
     */
    public static PersistToDb getInstance() throws PersistException{
        if ( mInstance == null ){
            mInstance = new PersistToDb();
        }
        return mInstance;
    }
    
    private final Connection mConnection;
    private final static String C_TWEET_TABLE_NAME = "sss_tweet_table";
    private final String C_DEF_SCHEMA_RES_FILE = "/build_schema.sql";
    
     
     /**
      * Initializes and connects to the persistent storage.
      * @failable in case there's some problem with the storage.
      * 
      * @throws PersistException in case there was problem connecting.
      * 
      */
     private PersistToDb() throws PersistException, PersistFatalException
     {
        try {
            MySqlProperties properties = new MySqlProperties();
            mConnection = properties.getNewConnection();
            
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
            dataSource.setUsername(properties.getUser());
            dataSource.setPassword(properties.getPassword());
            System.out.println("Connection url: [" + properties.getConnectionUrl() + "]");
            dataSource.setUrl(properties.getConnectionUrl());
            
            mJdbcTemplate = new JdbcTemplate(dataSource);
            
            // make sure that all tables and corresponding "schema" is created.
            if ( !dataSchemaExists() ){
                createDataSchema();
            }
            
        } catch (IOException | SQLException ex) {
            //Logger.getLogger(PersistToDb.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistException(ex);
        }
     }
     
     
     
     
     /**
      * Returns latest tweets.
      * 
      *  @param limit should be 1 or larger value. Keep in mind that if you make this
      *         parameter too big, ie 100 000 or 1M, then you may run out of memory.
      *  @return valid list (may be of length 0 in case there're no values).
      */
    public List<TweetRecord> getLatestTweets(int limit) {
        //
        String query = String.format("SELECT * FROM `%s` LIMIT 0,%d", C_TWEET_TABLE_NAME, limit);
        
        // TODO: will this return empty valid list on no results?
        List<TweetRecord> results = mJdbcTemplate.query(query, new RowMapper<TweetRecord>() {

            @Override
            public TweetRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TweetRecord().
                        setId(rs.getInt(TweetRecord.FT_ID)).
                        setTweetId(rs.getLong(TweetRecord.FT_TWEET_ID)).
                        setScreenName(rs.getString(TweetRecord.FT_SCREEN_NAME)).
                        setTweet(rs.getString(TweetRecord.FT_TWEET)).
                        setTimeStamp(rs.getLong(TweetRecord.FT_TIMESTAMP));
            }

        });
        return results;
    }
     
     /**
      * Persists the tweet to DB.
      * 
     * @param status is a tweet which needs to be persisted.
     * 
      * @throws PersistFatalException in case it's the end
      * @throws PersistReconnectableException in case reconnection may help
      */
     public void persistTweet(Status status) throws PersistException
     {
         try {
            StatementWrapper stWrapper = new StatementWrapper(C_TWEET_TABLE_NAME, mConnection);
            stWrapper.setTweet(status);
            //PreparedStatement pstmt = mConnection.prepareStatement("???");
             
             
            //PersistQuery pq = new PersistQuery(C_TWEET_TABLE_NAME);
            //pq.setTweet(status);
            
            int rez = stWrapper.executeUpdate();
           
            //executeUpdate(pq.renderToString());
         }
         catch(SQLException sqex){
             throw new PersistException(sqex);
         }
     }
     
     /**
      * Reconnects to the persistent storage.
      * 
      */
     public void reconnect(){
         throw new UnsupportedOperationException("Not implemented");
     }
     
     /**
      * Creates initial db schema, in case there's no db schema exists.
      */
     private void createSchema(){
         String createSchema = MySqlProperties.readResource("build_schema.query");
         
     
     }

    /**
     * Executes UPDATE/INSERT/DELETE statement or any other statement which doesn't
     * return results.
     * 
     * @param pq query string

     * @throws SQLException ?does it?
     * @deprecated I kinda don't use this method?
     * @returns count of the results (?affected rows?)
     */
    private int executeUpdate(String pq) throws SQLException
    {
//        ResultSet rs = null;
        Statement st = null;
        try{
            System.out.println("Performing query ["  + pq + "]");
            st = mConnection.createStatement();
            int rez = st.executeUpdate(pq.toString());
            return rez;
        }
        finally{
//            if ( rs != null ){
//                try {
//                    rs.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PersistToDb.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            
            if ( st != null ){
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PersistToDb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }    
    
    /**
     * Executes query.
     * 
     * @param pq query string
     * @param callback callback to call on each result set, 
     *          if NULL then not be called, just results will be counted.
     * 
     * @throws SQLException 
     * 
     * @returns count of the results.
     */
    private int executeQuery(String pq, IOnResultSetReady callback) throws SQLException
    {
        ResultSet rs = null;
        Statement st = null;
        try{
            System.out.println("Performing query ["  + pq + "]");
            st = mConnection.createStatement();
            rs = st.executeQuery(pq.toString());
            int resultCount = 0;
            while ( rs.next() ){
                resultCount++;
                // what's the story with exception in here?
                // if there will be an exception, then what happens is that this
                // method itself will throw an sql exception? And it will be raised to
                // the above level? 
                if ( callback != null) {
                    callback.onResultSet(rs);
                }
            }
            
            return resultCount;
        }
        finally{
            if ( rs != null ){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PersistToDb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if ( st != null ){
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PersistToDb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }

    /**
     * Creates schema (data structures with tables and stuff).
     * 
     */
    private void createDataSchema() throws SQLException {
        String query =  MySqlProperties.readResource(C_DEF_SCHEMA_RES_FILE);
        executeUpdate(query);
    }

    /**
     * Verifies if underlying data structures are created.
     * 
     * Just checks if the table exists.
     * 
     * @return 
     */
    private boolean dataSchemaExists() throws SQLException {
        
        return tableExists(C_TWEET_TABLE_NAME);
    }

    
    /**
     * Verifies if the table exists in current db.
     * 
     * @param tableName
     * @return 
     */
    private boolean tableExists(String tableName) throws SQLException {
        String query = String.format("SHOW TABLES LIKE '%s'", tableName);
        int resultCount = executeQuery(query, null);
        
        return (resultCount > 0);
    }
    
     
}
