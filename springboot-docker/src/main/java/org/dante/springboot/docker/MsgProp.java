package org.dante.springboot.docker;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "hello")
public class MsgProp {
	private String msg;
	private int sleep = 1;
	private String host = "127.0.0.1";
	private Integer port = 8080;
	private String cacheName = "未知";
}
