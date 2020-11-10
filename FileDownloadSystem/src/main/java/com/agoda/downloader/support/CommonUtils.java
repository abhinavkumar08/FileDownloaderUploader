package com.agoda.downloader.support;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.constants.ConfigFileConstants;
import com.mysql.jdbc.StringUtils;

/**
 * The Class CommonUtils.
 */
public final class CommonUtils {
	
	private static final Log LOGGER = LogFactory.getLog(CommonUtils.class);
	
	/**
	 * Instantiates a new common utils.
	 */
	private CommonUtils(){
		
	}
	
	/**
	 * Gets the file name from url.
	 *
	 * @param url the url
	 * @return the file name from url
	 */
	public static String getFileNameFromUrl(String url){
		
		if(url!=null){
			int endIndexOfHost = url.lastIndexOf("/");
			
			if(endIndexOfHost!=-1){
				return url.substring(endIndexOfHost+1, url.length());
			}
		}
		return null;
	}
	
	/**
	 * Gets the host from url.
	 *
	 * @param url the url
	 * @return the host from url
	 */
	public static String getHostFromUrl(String url){
		
		String urlWithoutProtocol = getUrlWithoutProtocol(url);
		if(urlWithoutProtocol!=null){
			int endIndexOfHost = urlWithoutProtocol.indexOf("/");
			
			if(endIndexOfHost!=-1){
				return urlWithoutProtocol.substring(0, endIndexOfHost);
			}
		}
		
		return null;
	}
	 
	/**
	 * Gets the complete file path from url.
	 *
	 * @param url the url
	 * @return the complete file path from url
	 */
	public static String getCompleteFilePathFromUrl(String url){
		
		String urlWithoutProtocol = getUrlWithoutProtocol(url);
		if(urlWithoutProtocol!=null){
			int endIndexOfHost = urlWithoutProtocol.indexOf("/");
			
			if(endIndexOfHost!=-1){
				return urlWithoutProtocol.substring(endIndexOfHost+1, urlWithoutProtocol.length());
			}
		}
		return null;
	}
	
	/**
	 * Gets the url without protocol.
	 *
	 * @param url the url
	 * @return the url without protocol
	 */
	private static String getUrlWithoutProtocol(String url){
		
		String urlWithoutProtocol = null;
		
		if(url!=null){
			String arr[] = url.split(":");
			
			String newUrl = arr[1];
			if(newUrl.length()>2){
				urlWithoutProtocol = newUrl.substring(2, newUrl.length());
			}
		}
		return urlWithoutProtocol;
		
	}
	
	public static boolean isConfigurationValid(Properties configProperties){
		
		String destPath = configProperties.getProperty(ConfigFileConstants.DESTINATION_PATH);
		String slowSpeedThreshold = configProperties.getProperty(ConfigFileConstants.SLOW_SPEED_THRESHOLD_IN_KB_PER_SECOND);
		String smallDataThreshold = configProperties.getProperty(ConfigFileConstants.SMALL_DATA_THRESHOLD_IN_KB);
		String dbHost = configProperties.getProperty(ConfigFileConstants.DB_HOST_NAME);
		String dbPassword = configProperties.getProperty(ConfigFileConstants.DB_PASSWORD);
		String dbPort = configProperties.getProperty(ConfigFileConstants.DB_PORT);
		String dbInstance = configProperties.getProperty(ConfigFileConstants.DB_INSTANCE);
		String dbUser = configProperties.getProperty(ConfigFileConstants.DB_USER);
		String threadpoolMinSize = configProperties.getProperty(ConfigFileConstants.THREAD_POOL_MIN_SIZE);
		String threadpoolMaxSize = configProperties.getProperty(ConfigFileConstants.THREAD_POOL_MAX_SIZE);
		
		
		if(StringUtils.isNullOrEmpty(destPath)){
			LOGGER.error("Download path cannot be left blank");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(slowSpeedThreshold)){
			LOGGER.error("Slow speed threshold cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(smallDataThreshold)){
			LOGGER.error("Small data threshold cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(dbHost)){
			LOGGER.error("Database host cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(dbPassword)){
			LOGGER.error("Database password cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(dbPort)){
			LOGGER.error("Database port cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(dbInstance)){
			LOGGER.error("Database Instance cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(dbUser)){
			LOGGER.error("Database user cannot be left blank.");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(threadpoolMinSize)){
			LOGGER.error("Thread pool min size cannot be left blank");
			return false;
		}
		
		if(StringUtils.isNullOrEmpty(threadpoolMaxSize)){
			LOGGER.error("Thread pool max size cannot be left blank");
			return false;
		}
		
		
		return true;
	}

}
