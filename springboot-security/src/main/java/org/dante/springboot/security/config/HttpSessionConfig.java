package org.dante.springboot.security.config;

import java.util.List;

import javax.servlet.http.HttpSessionListener;

import org.dante.springboot.security.security.SecHttpSessionEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import com.google.common.collect.Lists;

@EnableRedisHttpSession(redisNamespace = HttpSessionProperties.HTTP_REDIS_NAMESPACE, maxInactiveIntervalInSeconds = HttpSessionProperties.HTTP_INACTIVE_INTERVAL_SECONDS, redisFlushMode = RedisFlushMode.IMMEDIATE)
public class HttpSessionConfig {

	/**
	 * Spring-session 监听
	 * 
	 * @return
	 */
	@Bean
	public SecHttpSessionEventPublisher secHttpSessionEventPublisher() {
		return new SecHttpSessionEventPublisher();
	}

	@Bean
	public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter() {
		List<HttpSessionListener> listeners = Lists.newArrayList(secHttpSessionEventPublisher());
		return new SessionEventHttpSessionListenerAdapter(listeners);
	}

}
