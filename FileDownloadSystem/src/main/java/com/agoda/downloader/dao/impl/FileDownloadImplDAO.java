package com.agoda.downloader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.beans.DownloadStatus;
import com.agoda.downloader.beans.FileDownloadStats;
import com.agoda.downloader.dao.FileDownloadDAO;
import com.agoda.downloader.database.support.DBConnectionFactory;
import com.agoda.downloader.database.support.DBConstants;

/**
 * The Class FileDownloadImplDAO.
 */
public class FileDownloadImplDAO implements FileDownloadDAO {
	
	/** The Constant SPACE. */
	private static final String SPACE = " ";
	
	/** The Constant COMMA. */
	private static final String COMMA = ",";
	
	/** The Constant LOGGER. */
	private static final Log LOGGER = LogFactory.getLog(FileDownloadImplDAO.class);

	/* (non-Javadoc)
	 * @see com.agoda.downloader.dao.FileDownloadDAO#insertDownloadLog(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String insertDownloadLog(String sourceFile, String destinationFile, String protocol) throws SQLException {
		
		LOGGER.info("Inserting download request to DB.");
		Connection conn = DBConnectionFactory.getDbConnection();
		String downloadId = null;
		try {
			
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO").append(SPACE);
			query.append(DBConstants.FILE_DOWNLOAD_HISTORY.getName()).append(SPACE);
			query.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			Date currentTime = new Date();
			long timeInMilliSeconds  =currentTime.getTime();
			int random = (int)(Math.random()*273)/13;
			downloadId = String.valueOf(timeInMilliSeconds+random);
			PreparedStatement pStatement = conn.prepareStatement(query.toString());
			pStatement.setString(1, downloadId);
			pStatement.setString(2, sourceFile);
			pStatement.setString(3, destinationFile);
			pStatement.setTimestamp(4, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			pStatement.setTimestamp(5, null);
			pStatement.setString(6, DownloadStatus.DOWNLOADING.toString());
			pStatement.setString(7, protocol);
			pStatement.setString(8, null);
			pStatement.setString(9, null);
			
			pStatement.execute();
		  

	} catch (SQLException ex) {
			LOGGER.error("Exception occurred while trying to log download request to database");
			throw ex;

		} finally {
			if(conn!=null)
				try {
					LOGGER.info("Trying to close Database connection.");
					conn.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to close database connection.");
					throw e;
				}
		}
		return downloadId;

	}

	/* (non-Javadoc)
	 * @see com.agoda.downloader.dao.FileDownloadDAO#getFileDownloadStats(java.lang.String)
	 */
	public FileDownloadStats getFileDownloadStats(String fileSource) throws SQLException {
		
		LOGGER.info("Trying to fetch failure percentage from Database.");
		Connection conn = DBConnectionFactory.getDbConnection();
		FileDownloadStats fileDownloadStats = new FileDownloadStats();;
		
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT *").append(SPACE);
			query.append("From").append(SPACE);
			query.append(DBConstants.FILE_DOWNLOAD_STATS.getName()).append(SPACE);
			query.append("WHERE FILE_SOURCE = (?)");
		
			PreparedStatement pStatement = conn.prepareStatement(query.toString());
			pStatement.setString(1, fileSource);
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()){
				
				fileDownloadStats.setDownloadSuccessCount(resultSet.getInt("DOWNLOAD_SUCCESS_COUNT"));
				fileDownloadStats.setDownloadFailureCount(resultSet.getInt("DOWNLOAD_FAILED_COUNT"));
			}
		  
	} catch (SQLException ex) {
			LOGGER.error("Exception occurred while trying to fetch failure percentage.");
			throw ex;

		} finally {
			if(conn!=null)
				try {
					LOGGER.info("Trying to close Database connection.");
					conn.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to close database connection.");
					throw e;
				}
		}
		return fileDownloadStats;

	}

	/* (non-Javadoc)
	 * @see com.agoda.downloader.dao.FileDownloadDAO#updateDownloadStatusAndTime(com.agoda.downloader.beans.DownloadStatus, java.lang.String)
	 */
	public void updateDownloadLog(DownloadStatus status, String downloadId, String downloadSpeed, String dataSize) throws SQLException {

		LOGGER.info("Updating download log.");
		Connection conn = DBConnectionFactory.getDbConnection();
		try {
			
			StringBuilder query = new StringBuilder();
			query.append("UPDATE").append(SPACE);
			query.append(DBConstants.FILE_DOWNLOAD_HISTORY.getName()).append(SPACE);
			query.append("SET STATUS = (?)").append(COMMA);
			query.append("DOWNLOAD_END_TIME = (?)").append(COMMA);
			query.append("DATA_SIZE = (?)").append(COMMA);
			query.append("DOWNLOAD_SPEED = (?)").append(SPACE);
			query.append("WHERE DOWNLOAD_ID=(?)");
			
			PreparedStatement pStatement = conn.prepareStatement(query.toString());
			pStatement.setString(1, status.toString());
			pStatement.setTimestamp(2, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			pStatement.setString(3, dataSize);
			pStatement.setString(4, downloadSpeed);
			pStatement.setString(5, downloadId);
			
			pStatement.execute();
		  

	} catch (SQLException ex) {
			LOGGER.error("Exception occurred while trying to update download log entry.");
			throw ex;

		} finally {
			if(conn!=null)
				try {
					LOGGER.info("Trying to close Database connection.");
					conn.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to close database connection.");
					throw e;
				}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.agoda.downloader.dao.FileDownloadDAO#updateFailurePercentage(com.agoda.downloader.beans.FileDownloadStats, java.lang.String)
	 */
	public void updateFailurePercentage(FileDownloadStats fileDownloadStatsUpdated, String sourceFile) throws SQLException{
		
		LOGGER.info("Updating failure percentage.");
		Connection conn = DBConnectionFactory.getDbConnection();
		try {
			
			int successCount = fileDownloadStatsUpdated.getDownloadSuccessCount();
			int failedCount = fileDownloadStatsUpdated.getDownloadFailureCount();
			
			StringBuilder query = new StringBuilder();
			query.append("UPDATE").append(SPACE);
			query.append(DBConstants.FILE_DOWNLOAD_STATS.getName()).append(SPACE);
			query.append("SET DOWNLOAD_SUCCESS_COUNT= (?)").append(COMMA);
			query.append("DOWNLOAD_FAILED_COUNT= (?)").append(COMMA);
			query.append("DOWNLOAD_FAILURE_PERCENTAGE= (?)").append(SPACE);
			
			query.append("WHERE FILE_SOURCE=(?)");
			
			PreparedStatement pStatement = conn.prepareStatement(query.toString());
			pStatement.setInt(1, successCount);
			pStatement.setInt(2, failedCount);
			DecimalFormat format = new DecimalFormat("##.00");
			long totalCount = successCount + failedCount;
			double failurePercentage = ((double)failedCount/(double)totalCount)*100;
			String rounOffFailurePercentage = format.format(failurePercentage);
			pStatement.setDouble(3, Double.parseDouble(rounOffFailurePercentage));
			pStatement.setString(4, sourceFile);
			
			pStatement.execute();
		  

	} catch (SQLException ex) {
			LOGGER.error("Exception occurred while trying to update failure percentage.");
			throw ex;

		} finally {
			if(conn!=null)
				try {
					LOGGER.info("Trying to close Database connection.");
					conn.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to close database connection.");
					throw e;
				}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.agoda.downloader.dao.FileDownloadDAO#insertFailurePercentage(java.lang.String, int, int)
	 */
	public void insertFailurePercentage(String fileSource, int successCount, int failureCount) throws SQLException{
		
		LOGGER.info("Inserting record to download stats table.");
		Connection conn = DBConnectionFactory.getDbConnection();
		try {
			
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO").append(SPACE);
			query.append(DBConstants.FILE_DOWNLOAD_STATS.getName()).append(SPACE);
			query.append("VALUES ( ?, ?, ?, ?)");
			
			PreparedStatement pStatement = conn.prepareStatement(query.toString());
			pStatement.setString(1, fileSource);
			pStatement.setInt(2, successCount);
			pStatement.setInt(3, failureCount);
			pStatement.setInt(4, failureCount*100);
			
			pStatement.execute();
		  

	} catch (SQLException ex) {
			LOGGER.error("Exception occurred while trying to insert data to download stats table.");
			throw ex;

		} finally {
			if(conn!=null)
				try {
					LOGGER.info("Trying to close Database connection.");
					conn.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while trying to close database connection.");
					throw e;
				}
		}
		
	}

}
