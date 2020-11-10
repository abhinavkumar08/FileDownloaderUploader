package com.agoda.downloader.exception;

/**
 * The Class DownloadException.
 */
public class DownloadException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new download exception.
	 */
	public DownloadException() {
		super();
	}

	/**
	 * Instantiates a new download exception.
	 *
	 * @param message the message
	 */
	public DownloadException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new download exception.
	 *
	 * @param cause the cause
	 */
	public DownloadException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new download exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}

}
