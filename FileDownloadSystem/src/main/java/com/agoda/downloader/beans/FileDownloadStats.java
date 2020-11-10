package com.agoda.downloader.beans;
/**
 * The Class FileDownloadStats.
 */
public class FileDownloadStats {
	
	/** The download success count. */
	private int downloadSuccessCount;
	
	/** The download failure count. */
	private int downloadFailureCount;

	/**
	 * Gets the download success count.
	 *
	 * @return the download success count
	 */
	public int getDownloadSuccessCount() {
		return downloadSuccessCount;
	}

	/**
	 * Sets the download success count.
	 *
	 * @param downloadSuccessCount the new download success count
	 */
	public void setDownloadSuccessCount(int downloadSuccessCount) {
		this.downloadSuccessCount = downloadSuccessCount;
	}

	/**
	 * Gets the download failure count.
	 *
	 * @return the download failure count
	 */
	public int getDownloadFailureCount() {
		return downloadFailureCount;
	}

	/**
	 * Sets the download failure count.
	 *
	 * @param downloadFailureCount the new download failure count
	 */
	public void setDownloadFailureCount(int downloadFailureCount) {
		this.downloadFailureCount = downloadFailureCount;
	}
	
	

}
