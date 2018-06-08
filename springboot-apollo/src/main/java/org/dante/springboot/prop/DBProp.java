package org.dante.springboot.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @ConfigurationProperties如果需要在Apollo配置变化时自动更新注入的值，需要配合Spring Cloud的RefreshScope使用
 * 
 * @author dante
 */
@Data
@ConfigurationProperties(prefix = "db")
public class DBProp {
	private String name;
	private String url;
}
