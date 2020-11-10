package com.agoda.downloader.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.agoda.downloader.exception.DownloadException;

public class FileUtilsTest {
	
	
	@Test
	public void downloadFileUsingStreamTest() throws DownloadException{
		
		String text = "Hello";
		String destPathWithFileName = "/tmp";
		
		String fileName ="SampleFile.txt";
		InputStream stream =new ByteArrayInputStream(text.getBytes());
		FileUtils.downloadFileUsingStream(stream, destPathWithFileName, fileName);
		
	}

}
