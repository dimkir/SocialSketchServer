package org.socialsketch.server.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**
     * Returns instance (singleton style).
     * 
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
     public PersistToDb() throws PersistException, PersistFatalException
     {
        try {
            mConnection = new MySqlProperties().getNewConnection();
            
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
      * Persists the tweet to DB.
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
