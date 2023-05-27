package org.dante.springboot.cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.util.StrUtil;

@Configuration
public class RedissonConfig {
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //可以用"rediss://"来启用SSL连接
		singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        if(StrUtil.isNotEmpty(redisProperties.getPassword())) {
        	singleServerConfig.setPassword(redisProperties.getPassword());
        }
        
        singleServerConfig.setDatabase(redisProperties.getDatabase());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
	
}
