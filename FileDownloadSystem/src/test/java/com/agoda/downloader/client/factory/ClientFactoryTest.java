package com.agoda.downloader.client.factory;

import java.util.Properties;

import org.testng.annotations.Test;

import com.agoda.downloader.beans.Protocol;

public class ClientFactoryTest {
	
	
	@Test
	public void createFtpClientTest(){
		
		Properties properties = new Properties();
		properties.setProperty("sftp.port", "22");
		properties.setProperty("sftp.user", "sftpuser");
		properties.setProperty("sftp.password", "sftppassword");
		
		properties.setProperty("ftp.port", "21");
		properties.setProperty("ftp.user", "ftpuser");
		properties.setProperty("ftp.password", "ftppassword");
		
		ClientFactory.createClient(Protocol.FTP, properties);
		
	}
	
	@Test
	public void createSftpClientTest(){
		
		Properties properties = new Properties();
		properties.setProperty("sftp.port", "22");
		properties.setProperty("sftp.user", "sftpuser");
		properties.setProperty("sftp.password", "sftppassword");
		
		properties.setProperty("ftp.port", "21");
		properties.setProperty("ftp.user", "ftpuser");
		properties.setProperty("ftp.password", "ftppassword");
		
		ClientFactory.createClient(Protocol.SFTP, properties);
		
	}
	
	
	@Test
	public void createHTttpClientTest(){
		
		Properties properties = new Properties();
		
		ClientFactory.createClient(Protocol.HTTPS, properties);
		
	}

}
