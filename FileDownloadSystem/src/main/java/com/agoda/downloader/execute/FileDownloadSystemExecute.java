package com.agoda.downloader.execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.beans.DownloadStatus;
import com.agoda.downloader.beans.FileDownloadStats;
import com.agoda.downloader.beans.Protocol;
import com.agoda.downloader.client.exception.ClientException;
import com.agoda.downloader.client.factory.ClientFactory;
import com.agoda.downloader.constants.ConfigFileConstants;
import com.agoda.downloader.dao.FileDownloadDAO;
import com.agoda.downloader.dao.impl.FileDownloadImplDAO;
import com.agoda.downloader.exception.DownloadException;
import com.agoda.downloader.support.CommonUtils;
import com.agoda.downloader.support.FileUtils;

/**
 * The Class FileDownloadSystemExecute.
 */
public class FileDownloadSystemExecute {
	
	/** The Constant LOGGER. */
	private static final Log LOGGER = LogFactory.getLog(FileDownloadSystemExecute.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClientException 
	 * @throws DownloadException 
	 * @throws Exception the exception
	 * 
	 */
	public void download(String fileLocationDetail, Properties configProperties) throws IOException, SQLException, ClientException, DownloadException  {
		
		
		FileDownloadDAO fileDownloadDAO = new FileDownloadImplDAO();
		FileDownloadStats fileDownloadStats = null;
		InputStream stream = null;
		
		Client client = null;
		String downloadId = null;
		int failureCount=0;
		
		String destPath = null;
		String fileName = null;
		String dataSizeThreshold = null;
		String downloadSpeedThreshold = null;
		String downloadSpeed = "SLOW";
		String dataSize = "SMALL";
		
		
		try {
		    destPath = configProperties.getProperty(ConfigFileConstants.DESTINATION_PATH);
		    
		    dataSizeThreshold = configProperties.getProperty(ConfigFileConstants.SMALL_DATA_THRESHOLD_IN_KB);
		    long dataSizeThresholdInLong = 0l;
		    if(dataSizeThreshold!=null && !dataSizeThreshold.isEmpty()){
		    	dataSizeThresholdInLong = Long.parseLong(dataSizeThreshold);
		    }
		    
		    downloadSpeedThreshold = configProperties.getProperty(ConfigFileConstants.SLOW_SPEED_THRESHOLD_IN_KB_PER_SECOND);
		    long downloadSpeedThresholdInLong = 0l;
		    if(downloadSpeedThreshold!=null && !downloadSpeedThreshold.isEmpty()){
		    	downloadSpeedThresholdInLong = Long.parseLong(downloadSpeedThreshold);
		    }
		    
		    if(fileLocationDetail!=null){
		    	
		    	String fileDetails[] = fileLocationDetail.split(":");
		    	if(fileDetails.length==2){
		    		String protocol = fileDetails[0].toUpperCase();
		    		client = ClientFactory.createClient(Protocol.valueOf(protocol), configProperties);
		    		
		    		LOGGER.info("Initiating Download....");
		    		
		    		long startDownloadTime = new Date().getTime();
		    		//Inserting download details to Database.
		    		downloadId = fileDownloadDAO.insertDownloadLog(fileLocationDetail, destPath, protocol);
		    		//fetching download stats( success and failure count)
		    		fileDownloadStats = fileDownloadDAO.getFileDownloadStats(fileLocationDetail);
		    		
		    		int successCount = fileDownloadStats.getDownloadSuccessCount();
		    		failureCount = fileDownloadStats.getDownloadFailureCount();
		    		
		    		//If its the first time the file is downloaded.
		    		if(successCount==0 && failureCount==0){
		    			fileDownloadDAO.insertFailurePercentage(fileLocationDetail, successCount, failureCount);
		    		}
		    		
		    		stream = client.getInputStream(fileLocationDetail);
		    		
		    		fileName = CommonUtils.getFileNameFromUrl(fileLocationDetail);
		    		long fileSizeInKB = FileUtils.downloadFileUsingStream(stream, destPath, fileName);
		    		
		    		long endDownloadTime = new Date().getTime();
		    		
		    		long timeToDownload = (endDownloadTime - startDownloadTime)/1000;
		    		LOGGER.info("Download time :"+timeToDownload);
		    		LOGGER.info("File size in KB "+fileSizeInKB);
		    		long downloadRateInKbPerSec = fileSizeInKB/timeToDownload;
		    		
		    		if(fileSizeInKB>=dataSizeThresholdInLong){
		    			dataSize = "BIG";
		    		}
		    		
		    		if(downloadRateInKbPerSec>downloadSpeedThresholdInLong){
		    			downloadSpeed = "FAST";
		    		}
		    		
		    		//update DB success count and status.
		    		successCount++;
		    		
		    		fileDownloadStats.setDownloadSuccessCount(successCount);
		    		fileDownloadDAO.updateDownloadLog(DownloadStatus.DOWNLOADED, downloadId, downloadSpeed, dataSize);
		    		fileDownloadDAO.updateFailurePercentage(fileDownloadStats, fileLocationDetail);
		    		
		    	}
		    }
		} catch (ClientException e) {
			
			LOGGER.error("Error occurred while trying to download input File using client : "+ client.getClass().toString() +e.getMessage());
			fileDownloadStats.setDownloadFailureCount(++failureCount);
			fileDownloadDAO.updateDownloadLog(DownloadStatus.FAILED, downloadId, downloadSpeed, dataSize);
    		fileDownloadDAO.updateFailurePercentage(fileDownloadStats, fileLocationDetail);
			
			throw e;
		} catch (DownloadException e) {
			LOGGER.error("Error occurred while trying to download the input file. "+ e.getMessage());
			fileDownloadStats.setDownloadFailureCount(++failureCount);
			fileDownloadDAO.updateDownloadLog(DownloadStatus.FAILED, downloadId, downloadSpeed, dataSize);
    		fileDownloadDAO.updateFailurePercentage(fileDownloadStats, fileLocationDetail);
    		
    		//IF download fails in the middle, the file created will be deleted. This is to 
    		//handle no partial data storage on local.
    		Files.deleteIfExists(Paths.get(destPath+"/"+fileName));
			throw e;
		} catch (SQLException e) {
			LOGGER.error("Error occurred while trying to log entry in Database "+e.getMessage());
			throw e;
		}
		
		finally{
			try{
				if(stream!=null){
					LOGGER.info("Trying to close stream.");
					stream.close();
				}
				
			}
			catch (IOException e) {
				LOGGER.error("Error occurred while closing stream. "+e.getMessage());
				throw e;
			}
		}
	}
	
	public void executeBatch() throws IOException, SQLException, ClientException, DownloadException{
		
		  final Properties configProperties = new Properties();
		  InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("fileDownloadSystem.properties");
		  configProperties.load(inputStream);
		  
		  if(!CommonUtils.isConfigurationValid(configProperties)){
			  LOGGER.warn("Please enter valid configuration.");
			  LOGGER.info("Terminating program......");
			  return;
		  }
		  
		 String threadpoolMinSize = configProperties.getProperty(ConfigFileConstants.THREAD_POOL_MIN_SIZE);
		 int threadpoolMinSizeInt = Integer.parseInt(threadpoolMinSize);
		 
		 String threadpoolMaxSize = configProperties.getProperty(ConfigFileConstants.THREAD_POOL_MAX_SIZE);
		 int threadpoolMaxSizeInt = Integer.parseInt(threadpoolMaxSize);
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(threadpoolMinSizeInt, threadpoolMaxSizeInt , 1, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE));
		BufferedReader inputReader = null;
		try {
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		String listOfUrls = inputReader.readLine();
		String []fileLocations = listOfUrls.split(",");
		
		for(final String fileLocation : fileLocations){
			
			executor.execute(new Runnable() {
				
				public void run() {
					try {
						download(fileLocation, configProperties);
					} catch (IOException e) {
						LOGGER.error("Error occurred while trying to download file. "+e.getMessage());
					} catch (SQLException e) {
						LOGGER.error("Error occurred while trying to perform DB operation. "+e.getMessage());
					} catch (ClientException e) {
						LOGGER.error("Error occurred while instatiating client. "+e.getMessage());
					} catch (DownloadException e) {
						LOGGER.error("Error occurred while downloading the file. "+e.getMessage());
					}
					
				}
			});
			
		}
		
		executor.shutdown();
		
		try {
			boolean finished = executor.awaitTermination(1, TimeUnit.DAYS);
			LOGGER.info("Waiting for thread termination " + finished);
		} catch (InterruptedException e) {
			LOGGER.info("Thread interrupted ");
		}
			
		} catch (IOException e) {
			LOGGER.error("Error occurred while trying to read input. "+e.getMessage());
			throw e;
		} finally {
			if(inputReader!=null){
				LOGGER.info("Trying to close input reader.");
				inputReader.close();
			}
		}
	}

}
