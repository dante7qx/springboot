package org.dante.springboot.cache.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

/**
 * https://www.jianshu.com/p/9de4d4932e07
 * 
 * @author dante
 *
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	/**
	 * 缓存管理器
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisKeySerializer redisKeySerializer) {
		// Key 序列化器
//		RedisSerializer<String> redisKeySerializer = new StringRedisSerializer();
		
		// Value 序列化器
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);  
		
		// 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisKeySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
		
		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.build();
		
        return cacheManager;
	}
	
	/**
	 * 缓存 Key 的生成策略，该参数与key是互斥的
	 * 
	 */
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
	           @Override
	           public Object generate(Object o, Method method, Object... objects) {
	              StringBuilder sb = new StringBuilder();
	              sb.append(o.getClass().getName());
	              sb.append(method.getName());
	              for (Object obj : objects) {
	                  sb.append(obj.toString());
	              }
	              return sb.toString();
	           }
	       };
	}

	/**
	 * 缓存解析器
	 */
	@Override
	public CacheResolver cacheResolver() {
		return super.cacheResolver();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return super.errorHandler();
	}

}
