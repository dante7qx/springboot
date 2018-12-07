package org.dante.springboot.ftp.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spirit.ftp")
public class FtpProperties {
	private String serverAddress;
	private int serverPort = 21;
	private String ftpUser;
	private String ftpPassword;
	private String basePath;
}
