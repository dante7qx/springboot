package org.dante.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考：
 * 
 * https://github.com/apache/incubator-shardingsphere-example
 * https://shardingsphere.apache.org/index_zh.html
 * https://www.jianshu.com/p/639253764705
 * 
 * @author dante
 *
 */
@SpringBootApplication
@MapperScan("org.dante.springboot.repository")
public class SpringbootShardingProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootShardingProxyApplication.class, args);
	}
}
