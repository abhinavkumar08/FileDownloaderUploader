package com.agoda.downloader.database.support;
/**
 * The Class DBConstants.
 */
public class DBConstants {

	/**
	 * The Class FILE_DOWNLOAD_HISTORY.
	 */
	public static final class FILE_DOWNLOAD_HISTORY {
	
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public static String getName() {
			return "FILE_DOWNLOAD_HISTORY";
		}

		/** The Constant DOWNLOAD_ID. */
		public final static String DOWNLOAD_ID = "DOWNLOAD_ID";
		
		/** The Constant FILE_SOURCE. */
		public final static String FILE_SOURCE = "FILE_SOURCE";
		
		/** The Constant FILE_DESTINATION. */
		public final static String FILE_DESTINATION = "FILE_DESTINATION";
		
		/** The Constant DOWNLOAD_START_TIME. */
		public final static String DOWNLOAD_START_TIME = "DOWNLOAD_START_TIME";
		
		/** The Constant DOWNLOAD_END_TIME. */
		public final static String DOWNLOAD_END_TIME = "DOWNLOAD_END_TIME";
		
		/** The Constant STATUS. */
		public final static String STATUS = "STATUS";
		
		/** The Constant PROTOCOL. */
		public final static String PROTOCOL = "PROTOCOL";
		
		/** The Constant IS_LARGE_DATA. */
		public final static String IS_LARGE_DATA = "IS_LARGE_DATA";
		
		/** The Constant IS_SLOW. */
		public final static String IS_SLOW = "IS_SLOW";

	}
	
	/**
	 * The Class FILE_DOWNLOAD_STATS.
	 */
	public static final class FILE_DOWNLOAD_STATS {
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public static String getName() {
			return "FILE_DOWNLOAD_STATS";
		}

		/** The Constant FILE_SOURCE. */
		public final static String FILE_SOURCE = "FILE_SOURCE";
		
		/** The Constant DOWNLOAD_SUCCESS_COUNT. */
		public final static String DOWNLOAD_SUCCESS_COUNT = "DOWNLOAD_SUCCESS_COUNT";
		
		/** The Constant DOWNLOAD_FAILED_COUNT. */
		public final static String DOWNLOAD_FAILED_COUNT = "DOWNLOAD_FAILED_COUNT";
		
		/** The Constant DOWNLOAD_FAILURE_PERCENTAGE. */
		public final static String DOWNLOAD_FAILURE_PERCENTAGE = "DOWNLOAD_FAILURE_PERCENTAGE";
		
	}

}
