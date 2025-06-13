package org.dante.springboot.cache.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class SpiritMultiLevelCacheService {

    private final CacheManager localCacheManager;
    private final CacheManager distributedCacheManager;

    public SpiritMultiLevelCacheService(@Qualifier("caffeineCacheManager") CacheManager localCacheManager,
                                        @Qualifier("redissonCacheManager") CacheManager distributedCacheManager) {
        this.localCacheManager = localCacheManager;
        this.distributedCacheManager = distributedCacheManager;
    }

    /**
     * 多级缓存获取数据
     */
    public <T> T getCache(String cacheName, String key,
                                        Class<T> type, Supplier<T> dataLoader) {
        // 1. 先从本地缓存获取
        Cache localCache = localCacheManager.getCache(cacheName);
        if (localCache != null) {
            Cache.ValueWrapper wrapper = localCache.get(key);
            if (wrapper != null) {
                log.trace("Hit local cache: {}", key);
                return type.cast(wrapper.get());
            }
        }

        // 2. 从分布式缓存获取
        Cache distributedCache = distributedCacheManager.getCache(cacheName);
        T value = null;
        if (distributedCache != null) {
            Cache.ValueWrapper wrapper = distributedCache.get(key);
            if (wrapper != null) {
                value = type.cast(wrapper.get());
                log.trace("Hit distributed cache: {}", key);

                // 回写到本地缓存
                if (localCache != null) {
                    localCache.put(key, value);
                }
                return value;
            }
        }

        // 3. 从数据源加载
        value = dataLoader.get();
        if (value != null) {
            log.debug("Loaded from db: {}", key);

            // 写入分布式缓存
            if (distributedCache != null) {
                distributedCache.put(key, value);
            }

            // 写入本地缓存
            if (localCache != null) {
                localCache.put(key, value);
            }
        }

        return value;
    }

    /**
     * 主动更新多级缓存
     */
    public <T> void putCache(String cacheName, String key, T value) {
        Cache localCache = localCacheManager.getCache(cacheName);
        Cache distributedCache = distributedCacheManager.getCache(cacheName);
        if (distributedCache != null) distributedCache.put(key, value);
        if (localCache != null) localCache.put(key, value);
    }

    /**
     * 清除多级缓存
     */
    public void evictCache(String cacheName, String key) {
        // 清除本地缓存
        Cache localCache = localCacheManager.getCache(cacheName);
        if (localCache != null) {
            localCache.evict(key);
        }

        // 清除分布式缓存
        Cache distributedCache = distributedCacheManager.getCache(cacheName);
        if (distributedCache != null) {
            distributedCache.evict(key);
        }

        log.debug("Evicted from multi-level cache: {}", key);
    }
}
