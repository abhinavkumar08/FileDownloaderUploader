package com.agoda.downloader.database.support;

import java.sql.SQLException;

import org.testng.annotations.Test;


@Test(expectedExceptions = SQLException.class)
public class DBConnectionFactoryTest {

	public void getDbConnectionTest() throws SQLException{
		
		DBConnectionFactory.getDbConnection();
	
		
	}
	
}
