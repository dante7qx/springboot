package org.dante.springboot.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="qrcode")
public class QrCodeProperties {
	
	private int codeSize = 300;
	
	private int logoWidth = 60;
	
	private int logoHeight = 60;
}
