package com.agoda.downloader.client;

import java.util.Properties;

import org.testng.annotations.Test;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;

public class FtpClientTest {
	
	@Test(expectedExceptions = ClientException.class)
	public void getInputStreamTest() throws ClientException{
		
		Properties properties = new Properties();
		properties.setProperty("ftp.port", "21");
		properties.setProperty("ftp.user", "ftpuser");
		properties.setProperty("ftp.password", "ftppassword");
		
		Client client = new FtpClient(properties);
		client.getInputStream("ftp://and.also.this/file.csv");
		
	}
	
}
