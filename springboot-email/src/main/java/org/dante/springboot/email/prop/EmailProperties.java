package org.dante.springboot.email.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="sprit.email")
public class EmailProperties {

	/**
	 * 发件人
	 */
	private String from;
}
