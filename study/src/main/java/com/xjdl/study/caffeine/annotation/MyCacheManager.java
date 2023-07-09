package com.xjdl.study.caffeine.annotation;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.caffeine.CaffeineCacheManager;

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
@ConditionalOnClass({Caffeine.class, CaffeineCacheManager.class})
public @interface MyCacheManager {
    String CACHE_MANAGER = "myCaffeineCacheManager";

    String cacheNames() default CACHE_MANAGER;
}
