package com.agoda.downloader.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;

public class HttpClient implements Client {
	
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(HttpClient.class);

	/* (non-Javadoc)
	 * @see com.agoda.downloader.beans.Client#getInputStream(java.lang.String)
	 */
	public InputStream getInputStream(String downloadUrl) throws ClientException {
		
		InputStream stream = null;
		try {
			URL url = new URL(downloadUrl);
			LOGGER.info("Trying to open a connection to the url "+downloadUrl);
			stream  = url.openStream();
		} catch (MalformedURLException e) {
			LOGGER.error("Error occured while build the URL object, url entered may not be in correct format" +downloadUrl+", " +e.getMessage());
			throw new ClientException(e);
		} catch (IOException e) {
			LOGGER.error("Error occured while trying to hit the url " +downloadUrl+", " +e.getMessage());
			throw new ClientException(e);
		}
		return stream;
	}

}
