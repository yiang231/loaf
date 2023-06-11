package com.xjdl.study.config.caffeine;

import org.springframework.cache.annotation.CacheConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * CacheConfig 简化注解
 */
@Documented // 将元素包含到javadoc中
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，会被加载进JVM
@Target({TYPE}) // 运用场景
@CacheConfig(cacheNames = MyCacheManager.CACHE_MANAGER)
public @interface MyCacheManager {
    String CACHE_MANAGER = "myCaffeineCacheManager";

    String cacheNames() default CACHE_MANAGER;
}
