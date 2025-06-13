package org.dante.springboot.cache.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dante.springboot.cache.constant.CacheConsts;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class SpiritCacheConfig {

    private final RedisProperties redisProperties;

    /**
     * 一级缓存，本地 Caffeine
     */
    @Bean("caffeineCacheManager")
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(10_000)                                // 最大条目数
            .expireAfterWrite(10, TimeUnit.MINUTES)     // 写入后过期
            .expireAfterAccess(5, TimeUnit.MINUTES)     // 访问后过期
            .recordStats()                                      // 开启统计
            .removalListener((key, value, cause) -> {
                log.debug("Local cache removed: key={}, cause={}", key, cause);
            }));
        // 预定义缓存名称
        cacheManager.setCacheNames(CacheConsts.names());
        return cacheManager;
    }

    /**
     * 二级缓存，分布式 Redisson
     */
    @Bean("redissonCacheManager")
    public CacheManager redissonCacheManager() {
        long ttl = 30 * 60 * 1000L;       // 30分钟
        long maxIdle = 15 * 60 * 1000L;    // 15分钟
        Map<String, org.redisson.spring.cache.CacheConfig> configMap = new HashMap<>();
        CacheConsts.names().forEach(cacheName -> {
            configMap.put(cacheName, new org.redisson.spring.cache.CacheConfig(ttl, maxIdle));  // 统一 TTL
            // 也可单独设置 TTL
        });
        return new RedissonSpringCacheManager(redissonClient(), configMap);
    }

    /**
     * 多级缓存管理器  —  本地缓存未命中时查询分布式缓存
     */
    @Bean("multilevelCacheManager")
    public CacheManager multilevelCacheManager() {
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(Arrays.asList(
                caffeineCacheManager(),
                redissonCacheManager()
        ));
        cacheManager.setFallbackToNoOpCache(false);
        return cacheManager;
    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //可以用"redis://"来启用SSL连接
        singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        if(StrUtil.isNotEmpty(redisProperties.getPassword())) {
            singleServerConfig.setPassword(redisProperties.getPassword());
        }
        singleServerConfig.setDatabase(redisProperties.getDatabase());

        // 使用自定义 ObjectMapper，禁用默认类型信息
        ObjectMapper objectMapper = JsonMapper.builder()
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .build()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        config.setCodec(new JsonJacksonCodec(objectMapper));
        return Redisson.create(config);
    }

    /**
     * 自定义键生成器
     * 缓存注解中 @Cacheable、@CacheEvict 不要加参数 key=xx。否则不会走 KeyGenerator
     */
    @Bean("sptKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            // 默认前缀（如果未指定 cacheNames）
            String prefix = getPrefix(target, method);

            StringBuilder key = new StringBuilder();
            key
                .append(prefix).append(":")
                .append(target.getClass().getSimpleName())
                .append(":")
                .append(method.getName())
                .append(":");
            for (Object param : params) {
                if (param != null) {
                    key.append(param.toString()).append(":");
                }
            }
            if (!key.isEmpty()) {
                key.setLength(key.length() - 1);
            }
            return key.toString();
        };
    }

    private static String getPrefix(Object target, Method method) {
        String prefix = "_";
        // 从类或方法注解中提取 cacheNames 作为前缀
        CacheConfig cacheConfig = target.getClass().getAnnotation(CacheConfig.class);
        if (cacheConfig != null && cacheConfig.cacheNames().length > 0) {
            prefix = cacheConfig.cacheNames()[0];
        } else {
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            if (cacheable != null && cacheable.cacheNames().length > 0) {
                prefix = cacheable.cacheNames()[0];
            }
        }
        return prefix;
    }

    /*
     * 启用虚拟线程
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setVirtualThreads(true);
        executor.setThreadNamePrefix("vt-pool-");
        executor.setAwaitTerminationSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 即使启用了虚拟线程，仍可配置一些参数
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(60);

        return executor;
    }

}
