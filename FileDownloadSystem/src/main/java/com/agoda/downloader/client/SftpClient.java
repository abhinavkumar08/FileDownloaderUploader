package com.agoda.downloader.client;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agoda.downloader.beans.Client;
import com.agoda.downloader.client.exception.ClientException;
import com.agoda.downloader.constants.ConfigFileConstants;
import com.agoda.downloader.support.CommonUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
/**
 * The Class SftpClient.
 */
public class SftpClient implements Client {
	
	/** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(SftpClient.class);

	/** The port. */
	private String port;
	
	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;

	/**
	 * Instantiates a new sftp client.
	 *
	 * @param properties the properties
	 */
	public SftpClient(Properties properties) {
		this.port = properties.getProperty(ConfigFileConstants.SFTP_PORT);
		this.userName = properties.getProperty(ConfigFileConstants.SFTP_USER);
		this.password = properties.getProperty(ConfigFileConstants.SFTP_PASSWORD);
	}

	/* (non-Javadoc)
	 * @see com.agoda.downloader.beans.Client#getInputStream(java.lang.String)
	 */
	public InputStream getInputStream(String url) throws ClientException {
		JSch jsch = new JSch();
		Session session = null;
		InputStream inputStream = null;
		String host = CommonUtils.getHostFromUrl(url);
		String fileLocationOnServer = CommonUtils.getCompleteFilePathFromUrl(url);
		try {
			session = jsch.getSession(userName, host, Integer.parseInt(port));
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			LOGGER.info("Tyring to establish a sftp connection with host "+host+ " at port: "+port);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			inputStream = sftpChannel.get(fileLocationOnServer);

		} catch (JSchException e) {
			LOGGER.error("Error occurred while trying to get sftp session "+e.getMessage());
			throw new ClientException(e);
		} catch (SftpException e) {
			LOGGER.error("Error occurred while trying to connect to the host using sftp "+e.getMessage());
			throw new ClientException(e);
		} finally{
			if(session!=null){
				session.disconnect();
			}
			
		}
		return inputStream;
	}

}
