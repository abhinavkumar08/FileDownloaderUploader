package com.agoda.downloader.client.factory;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.beans.Protocol;
import com.agoda.downloader.client.FtpClient;
import com.agoda.downloader.client.HttpClient;
import com.agoda.downloader.client.SftpClient;

public class ClientFactory {
	
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(ClientFactory.class);
	
	public static Client createClient(Protocol protocolType, Properties properties){
		
		Client client = null;
		LOGGER.info("Calling factory class to get client object based on protocol "+protocolType.toString());
		
		switch(protocolType){
		
		case HTTPS :   case HTTP :
			LOGGER.info("Instantiating http/https client.");
			client = new HttpClient();
			break;
			
		case FTP :
			LOGGER.info("Instantiating ftp client.");
			client = new FtpClient(properties);
			break;
			
		case SFTP : 
			LOGGER.info("Instantiating sftp client.");
			client = new SftpClient(properties);
			break;
		
		}
		return client;
	}

}
