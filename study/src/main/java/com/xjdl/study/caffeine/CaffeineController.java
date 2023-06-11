package com.xjdl.study.caffeine;

import com.xjdl.study.myBatisPlus.MybatisPlusUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 必须指定 cacheNames 或者是 value ，SpringCache 使用 CacheManager 来管理多个缓存组件Cache ，
 * 这里的Cache组件就是根据该名称进行区分的
 */
@Slf4j
@RestController
@RequestMapping("/api/caffeine")
public class CaffeineController {
    @Autowired
    @Qualifier("myCaffeineCacheManager")
    CacheManager cacheManager;

    public static final String MP_USER = "mpUser";
    public static final String JPA_USER = "jpaUser";

    /**
     * 清除所有缓存
     * <p>
     * Caching 复合注解
     * 定义多种缓存行为
     * <p>
     * 其他缓存注解类型见
     *
     * @see MybatisPlusUserServiceImpl
     */
    @GetMapping("/evict")
    @Caching(
            cacheable = {},
            put = {},
            evict = {
                    @CacheEvict(
                            cacheNames = {MP_USER, JPA_USER}
                            // 方法调用后清理
//                            , allEntries = true
                            // 方法调用前清理
                            , beforeInvocation = true
                    )
            }
    )
    public void evict() {
        log.warn("{}, {} 指定缓存已清除", MP_USER, JPA_USER);
    }

    /**
     * 使用API的方式管理缓存
     */
    @GetMapping("/allComponent")
    public void allComponent() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            log.info("当前存在的缓存组件总数 {}\n\t{}", cacheNames.size(), cacheNames);
        }
//        Cache cache = cacheManager.getCache();
    }
}
