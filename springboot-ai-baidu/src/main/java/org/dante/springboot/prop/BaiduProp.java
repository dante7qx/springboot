package org.dante.springboot.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "face.baidu")
public class BaiduProp {
	private String appId;
	private String apiKey;
	private String secretKey;
}
