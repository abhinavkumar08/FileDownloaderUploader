package com.agoda.downloader.beans;

import java.io.InputStream;

import com.agoda.downloader.client.exception.ClientException;

/**
 * The Interface Client.
 */
public interface Client {
	
	/**
	 * Gets the input stream.
	 *
	 * @param sourceFileName the source file name
	 * @return the input stream
	 * @throws ClientException the client exception
	 */
	public InputStream getInputStream(String sourceFileName) throws ClientException;

}
