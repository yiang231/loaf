package com.xjdl.study.caffeine.config;

import com.xjdl.study.caffeine.CaffeineTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置缓存管理器
 */
@Configuration
@Slf4j
public class CaffeineConfig {
    @Bean("myCaffeineCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(CaffeineTest.manual());
        return caffeineCacheManager;
    }
}
