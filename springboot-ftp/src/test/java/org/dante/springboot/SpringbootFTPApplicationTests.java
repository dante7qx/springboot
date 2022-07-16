package org.dante.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.dante.springboot.ftp.factory.FtpClientFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootFTPApplicationTests {
	
	@Autowired
	private FtpClientFactory ftpClientFactory;
	
	@Test
	public void upload() throws Exception {
		FTPClient ftpClient = ftpClientFactory.getInstance();
		File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
		try {
			boolean storeFile = ftpClient.storeFile("test.txt", new FileInputStream(file));
			System.out.println("=============> Upload: " + storeFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftpClientFactory.release(ftpClient);
		}
	}
	
	@Test
	public void list() throws IOException {
		FTPClient ftpClient = ftpClientFactory.getInstance();
//		FTPFile[] listFiles = ftpClient.listFiles("dante");
		FTPFile[] listFiles = ftpClient.listDirectories();
		for (FTPFile ftpFile : listFiles) {
			System.out.println("===========> " + ftpFile.getName());
		}
		ftpClientFactory.release(ftpClient);
	}
}
