package org.dante.springboot.ftp.factory;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.dante.springboot.ftp.prop.FtpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FtpClientFactory {
	@Autowired
	private FtpProperties ftpProperties;

	public FTPClient getInstance() {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(ftpProperties.getServerAddress(), ftpProperties.getServerPort());
			ftp.login(ftpProperties.getFtpUser(), ftpProperties.getFtpPassword());
			log.info("连接 Ftp Server [{}:{}], 响应 -> {}", ftpProperties.getServerAddress(), ftpProperties.getServerPort(), ftp.getReplyString());
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
	            ftp.disconnect();
	            throw new IOException("Exception in connecting to FTP Server");
	        }
			
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.makeDirectory(ftpProperties.getBasePath());
			ftp.changeWorkingDirectory(ftpProperties.getBasePath());
		} catch (IOException e) {
			log.error("连接 Ftp Server 失败.", e);
			release(ftp);
		}
		return ftp;
	}

	public void release(FTPClient ftp) {
		if (ftp == null) {
			return;
		}
		try {
			ftp.logout();
		} catch (IOException e) {
			log.error("FTP logout error.", e);
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					log.error("FTP disconnect error.", e);
				}
			}
		}
	}

}
