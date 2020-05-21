package org.dante.springboot.zookeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
	private boolean enabled;
	private String server;
	private String namespace;
	private String digest;
	private Integer sessionTimeoutMs;
	private Integer connectionTimeoutMs;
	private Integer maxRetries;
	private Integer baseSleepTimeMs;
}
