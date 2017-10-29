package org.dante.springboot.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {
	
	/**
	 *  其中 RedisConnectionFactory 默认使用 spring.redis.* 的配置，具体实现是JedisConnectionFactory
	 */
	@Autowired
	private RedisConnectionFactory connectionFactory;

	/*
	@Autowired
	private RedisProperties redisProperties;

	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		Pool pool = redisProperties.getPool();
		if (pool != null) {
			jedisPoolConfig.setMinIdle(pool.getMinIdle());
			jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
			jedisPoolConfig.setMaxTotal(pool.getMaxActive());
			jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait());
		}
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory connection1Factory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig());
		connectionFactory.setDatabase(redisProperties.getDatabase());
		connectionFactory.setHostName(redisProperties.getHost());
		connectionFactory.setPort(redisProperties.getPort());
		connectionFactory.setPassword(redisProperties.getPassword());
		return connectionFactory;
	}
	*/
	
	@Bean(name = "redisTemplate")
	RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(connectionFactory());
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
		return redisTemplate;
	}
	
	Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		return jackson2JsonRedisSerializer;
	}


}
