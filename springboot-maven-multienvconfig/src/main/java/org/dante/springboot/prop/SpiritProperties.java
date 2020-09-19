package org.dante.springboot.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spirit")
public class SpiritProperties {
	
	private String title;
	
	private String name;
	
	private String mqUrl;
	
	private String redisUrl;
	
	private AliPayProp aliPay;
}
