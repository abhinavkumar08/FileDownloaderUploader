package com.agoda.downloader.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;
import com.agoda.downloader.constants.ConfigFileConstants;
import com.agoda.downloader.support.CommonUtils;
/**
 * The Class FtpClient.
 */
public class FtpClient implements Client {
	
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(FtpClient.class);
	
	/** The port. */
	private String port;
	
	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new ftp client.
	 *
	 * @param properties the properties
	 */
	public FtpClient(Properties properties){
		this.port = properties.getProperty(ConfigFileConstants.FTP_PORT);
		this.userName = properties.getProperty(ConfigFileConstants.FTP_USER);
		this.password = properties.getProperty(ConfigFileConstants.FTP_PASSWORD);
	}

	/* (non-Javadoc)
	 * @see com.agoda.downloader.support.Client#getFileAsInputStream(java.lang.String)
	 */
	public InputStream getInputStream(String url) throws ClientException  {
		
		FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		String host = CommonUtils.getHostFromUrl(url);
		String fileLocationOnServer = CommonUtils.getCompleteFilePathFromUrl(url);
		try {
			LOGGER.info("Tyring to establish a ftp connection with host "+host+ " at port: "+port);
			ftpClient.connect(host, Integer.parseInt(port));
			ftpClient.login(userName, password);
		    ftpClient.enterLocalPassiveMode();
		    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		    inputStream = ftpClient.retrieveFileStream(fileLocationOnServer);
		    boolean success = ftpClient.completePendingCommand();
            if (success) {
               LOGGER.info("File retrieved as stream.");
            }
		
		} catch (NumberFormatException e) {
			LOGGER.error("Error occured while trying to connect to port "+port+", Port entered is not valid");
			throw new ClientException(e);
			
		} catch (SocketException e) {
			LOGGER.error("Network error occurred while trying to connect using ftp client" + e.getMessage());
			throw new ClientException(e);
		} catch (IOException e) {
			LOGGER.error("Error occured while trying to connect to host using ftp client" +e.getMessage());
			throw new ClientException(e);
		}finally {
			 try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	            	LOGGER.error("Error occured while trying to close ftp client" +ex.getMessage());
	    			throw new ClientException(ex);
		}
	}
		return inputStream;
 }
	
}
