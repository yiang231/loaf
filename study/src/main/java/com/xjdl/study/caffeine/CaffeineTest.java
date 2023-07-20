package com.xjdl.study.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.xjdl.study.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * caffeine使用
 */
@Slf4j
public class CaffeineTest {
    public static final String KEY = "test";
    public static Cache<Object, Object> manualCache;
    public static LoadingCache<Object, String> loadingCache;
    public static Map<String, String> CACHE_MAP = new HashMap<String, String>() {{
        put("1", "z");
        put("2", "x");
        put("3", "c");
        put("4", "v");
    }};
    public static AsyncCache<Object, Object> asyncCache;

    private static String getFromDataBase(Object key) {
        return "从数据库中获取 " + key + " 对应的缓存值";
    }

    /**
     * 手动创建缓存
     */
    Cache<Object, Object> manualBuild() {
        manualCache = manual().build();
        return manualCache;
    }

    public static Caffeine<Object, Object> manual() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                // 基于数量的淘汰机制
                .maximumSize(1000)

                // 基于权重的淘汰机制
//                .maximumWeight(2)
//                .weigher(new Weigher<Object, Object>() {
//                    @Override
//                    public @NonNegative int weigh(@NonNull Object key, @NonNull Object value) {
//                        // 用 key 或者是 value 来进行权重计算
//                        return (int) key;
//                    }
//                })

                // 基于时间的淘汰机制【三种】
                // 访问后到期和写入后到期
                // expireAfterAccess() 和 expireAfterWrite() 同时存在时，以后者为准
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // 最后一次读或者是写操作后，经过指定时间过期
                .expireAfterAccess(10, TimeUnit.SECONDS)
                // 自定义过期时间
//                .expireAfter(new Expiry<Object, Object>() {
//                    @Override
//                    public long expireAfterCreate(@NonNull Object key, @NonNull Object value, long currentTime) {
//                        return TimeUnit.SECONDS.toNanos(1);
//                    }
//
//                    @Override
//                    public long expireAfterUpdate(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
//                        return TimeUnit.SECONDS.toNanos(1);
//                    }
//
//                    @Override
//                    public long expireAfterRead(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
//                        return TimeUnit.SECONDS.toNanos(1);
//                    }
//                })

                // 基于引用的淘汰机制
//                // 生命周期是下次GC的时候，并且堆内存不够用
//                .softValues()
//                // 生命周期是下次GC的时候
//                .weakKeys()
//                .weakValues()

                // 定期清空过期数据 JDK9以上版本生效
                .scheduler(Scheduler.systemScheduler())
                // 记录命中信息
                .recordStats()
                // 淘汰监听机制，监听缓存被移除
                .removalListener(((key, value, cause) -> log.warn("淘汰通知\n\t{}={}\t{}", key, value, cause)));
    }

    /**
     * 同步创建缓存
     */
    LoadingCache<Object, String> sync() {
        loadingCache = Caffeine.newBuilder()
                .maximumSize(4)
                // refreshAfterWrite() 仅支持 LoadingCache
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .build(CaffeineTest::getFromDataBase);
        return loadingCache;
    }

    /**
     * 异步创建缓存
     */
    AsyncCache<Object, Object> asyncCache() {
        asyncCache = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .executor(Executors.newSingleThreadExecutor())
                .buildAsync();
        return asyncCache;
    }

    /**
     * 异步缓存获取数据
     */
    @Test
    void getFromAsync() throws ExecutionException, InterruptedException {
        MyUtils.getThreadName();
        asyncCache = asyncCache();
        CompletableFuture<Object> completableFuture = asyncCache.get(KEY, key -> {
            MyUtils.getThreadName();
            return getFromDataBase(key);
        });
        log.info("{}={}", KEY, completableFuture.get());
        MyUtils.getThreadName();
    }

    /**
     * 同步获取缓存
     * <p>
     * get()获取不到 key 对应的 value 时，会调用 build() 中的方法，并且将返回值插入回缓存
     */
    @Test
    void getFromSync() {
        loadingCache = sync();
        log.info("查询前缓存大小 {}", loadingCache.estimatedSize());
        log.info("{}={}", KEY, loadingCache.get(KEY));
        log.info("查询后缓存大小 {}", loadingCache.estimatedSize());
    }

    /**
     * refreshAfterWrite() 写操作后刷新
     * <p>
     * 在一定时间后进行了访问【第一次get还是原来的值，写后过期重新计时】，再访问后才是刷新后的值【第二次get新的值】
     */
    @Test
    void refreshAfterWrite() throws InterruptedException {
        loadingCache = sync();
        loadingCache.putAll(CACHE_MAP);
        Thread.sleep(2500);
        // z
        log.info("{}", loadingCache.getIfPresent("1"));
        Thread.sleep(2950);
        // 从数据库中获取 1 对应的缓存值
        log.info("{}", loadingCache.getIfPresent("1"));
    }

    /**
     * 获取缓存值
     */
    @Test
    void get() {
        manualCache = manualBuild();
        manualCache.putAll(CACHE_MAP);

        log.info("get {}", manualCache.get("1", v -> "IllegalArgument"));
        log.info("get {}", manualCache.get("5", v -> "IllegalArgument"));

        log.info("getIfPresent {}", manualCache.getIfPresent("2"));

        log.info("getAllPresent {}", manualCache.getAllPresent(CACHE_MAP.keySet()));
        log.info("getAll {}"
                , manualCache.getAll(Arrays.asList("2", "3"), objects -> {
                    Map<Object, Object> map = new HashMap<>();
                    objects.forEach(object -> map.put(object, manualCache.getIfPresent(object)));
                    return map;
                })
        );

        CacheStats stats = manualCache.stats();
        log.info("命中率 {}", stats.hitRate());
        log.info("被剔除的数量 {}", stats.evictionCount());
        log.info("加载新值花费的平均时间 {}", stats.averageLoadPenalty());
    }
}
