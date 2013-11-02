package org.socialsketch.server.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Helper Properties for mysql (also this is actually spec for the properties file.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class MySqlProperties extends Properties {
     private final String mResourcePath;
     
     private static final String MYSQL_USER = "db.mysql.user";
     private static final String MYSQL_PASSWORD = "db.mysql.password";
     private static final String MYSQL_DATABASE = "db.mysql.database";
     private static final String MYSQL_SERVER = "db.mysql.server";
     private static final String MYSQL_PORT = "db.mysql.port";
     
     /**
      * Those are default values and later in code it will be implemented to check
      * for whether these params are explicitly specified, and default values will
      * be used if not.
      */
     private static final String DEFAULT_MYSQL_PORT = "3306";
     private static final String DEFAULT_MYSQL_SERVER = "localhost";
     
     /**
      * Default location of the properties file is "default package" (root of your source directory)
      */
     private static final String C_DEFAULT_MYSQL_PROPERTIES_RESOURCE_PATH = "/mysql.properties";
     
     
     /**
      * Establishes and returns connection.
      * @return
      * @throws SQLException 
      */
     public Connection getNewConnection() throws SQLException
     {
        return DriverManager.getConnection(getConnectionUrl(), getUser(), getPassword());         
     }
     
     
     /**
      * Initializes mysql properties from default resource file:
      * {@link #C_DEFAULT_MYSQL_PROPERTIES_RESOURCE_PATH}
      * 
      * @throws IOException 
      */
     public MySqlProperties() throws IOException 
     {
         this(C_DEFAULT_MYSQL_PROPERTIES_RESOURCE_PATH);
     }
     
     public MySqlProperties(String resourcePath) throws IOException{
         super();
         mResourcePath = resourcePath;
         
         load(this.getClass().getResourceAsStream(resourcePath));
         
     }
     
     /**
      * ?
      * @return NULL if empty or unavailable.
      */
     String getUser(){
         return getProperty(MYSQL_USER);
     }
     
     
     /**
      * 
      * @return 
      */
     String getPassword(){
         return getProperty(MYSQL_PASSWORD);
     }

     
     String getDatabase(){
         return getProperty(MYSQL_DATABASE);
     }
    
     String getServer(){
         String server = getProperty(MYSQL_SERVER);
         if ( server == null ){
             return DEFAULT_MYSQL_SERVER;
         }
         return server;
     }
     
     String getPort(){
         
         String port = getProperty(MYSQL_PORT);
         if ( port == null ){
             return DEFAULT_MYSQL_PORT;
         }
         return port;
     }

     
    /**
     * Returns connection url (string).
     * @return 
     */
    String getConnectionUrl() {
        String url = String.format("jdbc:mysql://%s:%s/%s",
                                        getServer(),
                                        getPort(),
                                        getDatabase()
                                    );
        return url;
    }
    
}
