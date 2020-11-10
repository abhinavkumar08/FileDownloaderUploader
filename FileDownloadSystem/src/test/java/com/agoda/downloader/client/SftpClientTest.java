package com.agoda.downloader.client;

import java.util.Properties;

import org.testng.annotations.Test;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;

public class SftpClientTest {
	
	@Test(expectedExceptions = ClientException.class)
	public void getInputStreamTest() throws ClientException{
		
		Properties properties = new Properties();
		properties.setProperty("sftp.port", "22");
		properties.setProperty("sftp.user", "sftpuser");
		properties.setProperty("sftp.password", "sftppassword");
		
		Client client = new SftpClient(properties);
		client.getInputStream("sftp://and.also.this/file.csv");
		
	}

}
