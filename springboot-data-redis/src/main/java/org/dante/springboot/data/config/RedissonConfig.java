package org.dante.springboot.data.config;

import java.io.IOException;

import org.dante.springboot.data.prop.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.util.StrUtil;

@Configuration
@EnableCaching
public class RedissonConfig {
	
	private static final String REDIS_PROTOCOL_PREFIX = "redis://";
	private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
	
	@Autowired
	private RedisProperties redisProperties;

	@Autowired
	private RedissonProperties redissonProperties;

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean(RedissonClient.class)
	public RedissonClient redisson() throws IOException {
		String prefix = REDIS_PROTOCOL_PREFIX;
		if (redisProperties.isSsl()) {
			prefix = REDISS_PROTOCOL_PREFIX;
		}
		Config config = new Config();
		config.setThreads(redissonProperties.getThreads())
			.setNettyThreads(redissonProperties.getNettyThreads())
			.setCodec(JsonJacksonCodec.INSTANCE)
			.setTransportMode(redissonProperties.getTransportMode());

		RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
		// 使用单机模式
		config.useSingleServer()
			.setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
			.setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
			.setDatabase(redisProperties.getDatabase())
			.setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
			.setTimeout(singleServerConfig.getTimeout())
			.setRetryAttempts(singleServerConfig.getRetryAttempts())
			.setRetryInterval(singleServerConfig.getRetryInterval())
			.setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
			.setClientName(singleServerConfig.getClientName())
			.setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
			.setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
			.setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
			.setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
			.setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
			.setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());
		return Redisson.create(config);
	}
	
}
