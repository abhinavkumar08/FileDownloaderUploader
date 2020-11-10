package com.agoda.downloader.dao;

import java.sql.SQLException;

import com.agoda.downloader.beans.DownloadStatus;
import com.agoda.downloader.beans.FileDownloadStats;

/**
 * The Interface FileDownloadDAO.
 */
public interface FileDownloadDAO {
	
	/**
	 * Insert download log.
	 *
	 * @param sourceFile the source file
	 * @param destinationFile the destination file
	 * @param dataSize the data size
	 * @param dataSpeed the data speed
	 * @param protocol the protocol
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String insertDownloadLog(String sourceFile, String destinationFile, String protocol)  throws SQLException;
	
	/**
	 * Gets the file download stats.
	 *
	 * @param fileSource the file source
	 * @return the file download stats
	 * @throws SQLException the SQL exception
	 */
	public FileDownloadStats getFileDownloadStats(String fileSource)  throws SQLException;
	
	/**
	 * Update download status and time.
	 *
	 * @param status the status
	 * @param downloadId the download id
	 * @throws SQLException the SQL exception
	 */
	public void updateDownloadLog(DownloadStatus status, String downloadId, String downloadSpeed, String dataSize)  throws SQLException;
	
	/**
	 * Update failure percentage.
	 *
	 * @param fileDownloadStatsUpdated the file download stats updated
	 * @param sourceFile the source file
	 * @throws SQLException the SQL exception
	 */
	public void updateFailurePercentage(FileDownloadStats fileDownloadStatsUpdated, String sourceFile) throws SQLException;
	
	/**
	 * Insert failure percentage.
	 *
	 * @param fileSource the file source
	 * @param successCount the success count
	 * @param failureCount the failure count
	 * @throws SQLException the SQL exception
	 */
	public void insertFailurePercentage(String fileSource, int successCount, int failureCount) throws SQLException;

}
