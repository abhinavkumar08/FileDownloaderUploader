package com.agoda.downloader.client;

import org.testng.annotations.Test;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;

public class HttpClientTest {
	
	Client client = new HttpClient();
	
	@Test(expectedExceptions = ClientException.class)
	public void getFileAsInputStreamTest() throws ClientException{
		
		String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
		
		client.getInputStream(url);
		
		
	}
	
	
}