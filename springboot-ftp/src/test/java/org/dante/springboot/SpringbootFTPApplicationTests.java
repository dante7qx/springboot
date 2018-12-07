package org.dante.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.dante.springboot.ftp.factory.FtpClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
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
		} finally {
			ftpClientFactory.release(ftpClient);
		}
	}
	
	@Test
	public void list() throws IOException {
		FTPClient ftpClient = ftpClientFactory.getInstance();
		FTPFile[] listFiles = ftpClient.listFiles("/home/ftp/");
		for (FTPFile ftpFile : listFiles) {
			System.out.println("===========> " + ftpFile.getName());
		}
		ftpClientFactory.release(ftpClient);
	}
}
