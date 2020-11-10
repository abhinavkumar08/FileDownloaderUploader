package com.agoda.downloader.database.support;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.constants.ConfigFileConstants;

/**
 * A factory for creating DBConnection objects.
 */
public final class DBConnectionFactory {
	
	/** The prop. */
	private static Properties prop;
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(DBConnectionFactory.class);
	
	/**
	 * Instantiates a new DB connection factory.
	 */
	private DBConnectionFactory(){
	}
	
	
	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Properties getproperties() throws IOException{
    if (prop == null) {
        InputStream inputStream = DBConnectionFactory.class.getClassLoader().getResourceAsStream("fileDownloadSystem.properties");
        prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
    }
    return prop;
}

	/**
	 * Gets the prop.
	 *
	 * @return the prop
	 */
	public static Properties getProp() {
		return prop;
	}

	/**
	 * Gets the db connection.
	 *
	 * @return the db connection
	 * @throws SQLException the SQL exception
	 */
	public static  Connection getDbConnection() throws SQLException {
		
				try {
					
					getproperties();
					String hostname = prop.getProperty(ConfigFileConstants.DB_HOST_NAME);
					String port = prop.getProperty(ConfigFileConstants.DB_PORT);
					String dbInstance = prop.getProperty(ConfigFileConstants.DB_INSTANCE);
					String dbUser = prop.getProperty(ConfigFileConstants.DB_USER);
					String dbPassword = prop.getProperty(ConfigFileConstants.DB_PASSWORD);
					
					Class.forName("com.mysql.jdbc.Driver");
					
					LOGGER.info("Establishing Database Connection.");
					
					return DriverManager.getConnection(  
							"jdbc:mysql://"+hostname+":"+port+"/"+dbInstance,dbUser,dbPassword);  
					
				} catch (ClassNotFoundException e) {
					LOGGER.error("Error occurred while loading jdbc jar. "+e.getMessage());
					throw new SQLException(e);
					
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to get database connection. "+e.getMessage());
					throw e;
					
				} catch (IOException e) {
					LOGGER.error("Error occurred while trying to load properties file. "+e.getMessage());
					throw new SQLException(e);
				}  
	}

}

