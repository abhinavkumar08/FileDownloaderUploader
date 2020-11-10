package com.agoda.downloader.support;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CommonUtilsTest {
	
	
	
	@Test
	public void getFileNameFromUrlTest(){
		
		String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
		String fileName = CommonUtils.getFileNameFromUrl(url);
		Assert.assertEquals("dummy.pdf", fileName);
	}
	
	@Test
	public void getFileNameFromUrlTest2(){
		
		String url = null;
		String fileName = CommonUtils.getFileNameFromUrl(url);
		Assert.assertNull(fileName);
	}
	
	
	@Test
	public void getHostFromUrlTest(){
		
	String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
	String fileName = CommonUtils.getHostFromUrl(url);
	Assert.assertEquals("www.w3.org", fileName);
	}
	
	@Test
	public void getHostFromUrlTest2(){
		
	String url = null;
	String fileName = CommonUtils.getHostFromUrl(url);
	Assert.assertNull(fileName);
	}
	
	@Test
	public void getCompleteFilePathFromUrlTest(){
		
	String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
	String fileName = CommonUtils.getCompleteFilePathFromUrl(url);
	System.out.println(fileName);
	Assert.assertEquals("WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", fileName);
	}
	
	@Test
	public void getCompleteFilePathFromUrlTest2(){
		
	String url = null;
	String fileName = CommonUtils.getCompleteFilePathFromUrl(url);
	Assert.assertNull(fileName);
	}

}
