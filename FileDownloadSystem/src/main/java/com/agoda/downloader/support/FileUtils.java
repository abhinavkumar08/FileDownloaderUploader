package com.agoda.downloader.support;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.exception.DownloadException;

/**
 * The Class FileUtils.
 */
public final class FileUtils {
	
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(FileUtils.class);
	
	/**
	 * Instantiates a new file utils.
	 */
	private FileUtils(){
		
	}
	/**
	 * Download file using stream.
	 *
	 * @param inputStream the input stream
	 * @param destPathWithFileName the dest path with file name
	 * @param mode the mode
	 * @param fileName the file name
	 * @throws DownloadException the download exception
	 */
	public static long downloadFileUsingStream(InputStream inputStream, String destPathWithFileName, String fileName) throws DownloadException{
		
		LOGGER.info("Downloading file .."+fileName);
		
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		 long fileSizeInKB = 0;
		
		try{
			
			byte[] buffer = new byte[1024];
			bufferedInputStream = new BufferedInputStream(inputStream);
	        File newFile = new File(destPathWithFileName+"/"+fileName);
	        OutputStream os = new FileOutputStream(newFile);
	        bufferedOutputStream = new BufferedOutputStream(os);
	        int readCount = -1;
	       
	        
	        while ((readCount = bufferedInputStream.read(buffer)) != -1) {
	        	fileSizeInKB+=readCount;
	            bufferedOutputStream.write(buffer, 0, readCount);
	        }
	        
	        bufferedInputStream.close();
	        bufferedOutputStream.close();
	        
	        LOGGER.info("File downloaded.");
	        
		} catch (IOException e) {
			LOGGER.error("Error occurred while trying to download the file using stream : "+e.getMessage());
			throw new DownloadException(e);
		}finally{
			if(bufferedInputStream!=null && bufferedOutputStream!=null){
				 try {
					bufferedInputStream.close();
					bufferedOutputStream.close();
				} catch (IOException e) {
					LOGGER.error("Error occurred while trying to close input and output stream : "+e.getMessage());
					throw new DownloadException(e);
				}
			}
			
		}
		return fileSizeInKB;
	}
	
}
