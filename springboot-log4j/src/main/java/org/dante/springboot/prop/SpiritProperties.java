package org.dante.springboot.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spirit")
public class SpiritProperties {
	
	private String name;
	
	private AliPayProp aliPay;
}
