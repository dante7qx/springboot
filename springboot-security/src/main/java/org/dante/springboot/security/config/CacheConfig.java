package org.dante.springboot.security.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig {
	@Bean  
    @Primary  
    public CacheManager cacheManager() {  
		GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
            CacheBuilder.newBuilder().
            expireAfterWrite(120, TimeUnit.SECONDS).
            maximumSize(1000));
        return cacheManager;
    }  
}
