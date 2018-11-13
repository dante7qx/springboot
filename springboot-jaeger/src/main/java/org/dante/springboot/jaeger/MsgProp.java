package org.dante.springboot.jaeger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "hello")
public class MsgProp {
	private String msg;
	private String reqUrl;
	private int sleep = 1;
}
