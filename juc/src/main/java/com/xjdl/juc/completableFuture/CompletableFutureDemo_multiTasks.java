package com.xjdl.juc.completableFuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 多任务组合
@Slf4j
public class CompletableFutureDemo_multiTasks {
    @Test
    void test() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4
                , 8
                , 2L
                , TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(10)
                , new ThreadFactory() {
            int i = 1;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("pool-thread-" + i++);
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
        CompletableFuture<Void> futureTask1_void = CompletableFuture.runAsync(() -> {
            log.info("任务1");
        }, executor);

        CompletableFuture<Integer> futureTask2_int = CompletableFuture.supplyAsync(() -> {
//			throw new ArithmeticException();
            log.info("任务2");
            return 123;
        }, executor);

        CompletableFuture<Void> futureTask3_void = CompletableFuture.runAsync(() -> {
            log.info("任务3");
        }, executor);

        log.info("开始添加任务到集合！！");
        List<CompletableFuture<Integer>> futuresCollection = new ArrayList<>();
        futuresCollection.add(futureTask2_int);
        for (int i = 0; i < 10; i++) {
            int nums = i;
            CompletableFuture<Integer> subFuture = CompletableFuture.supplyAsync(() -> {
                log.info("sub task for Number {} !", nums);
                return nums;
            }, executor);
            futuresCollection.add(subFuture);
        }

        CompletableFuture<Integer> failureTask = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("执行失败");
        });

//        CompletableFuture<Integer> safeFutureTask = failureTask.handle((result, ex) -> {
//            log.info("判断 future 是否失败 {}", failureTask.isCompletedExceptionally());
//            if (ex != null) {
//                log.info("运行出现异常 handle {}", ex.getMessage());
//                return 999;
//            }
//            return (int) result;
//        });

        CompletableFuture<Integer> safeFutureTask = failureTask.exceptionally((result) -> {
            log.error("运行出现异常 exceptionally {}", String.valueOf(result));
            return 25;
        });

        // exceptionally
        // handle
        // whenComplete
        // allOf/anyOf
        // join get try/catch

        futuresCollection.add(safeFutureTask);
        // 以上异步任务全部完成,做另外一件事,比如保存数据库等操作
        CompletableFuture.allOf(futuresCollection.toArray(new CompletableFuture[0]))
                .whenCompleteAsync((unused, throwable) -> {
                    if (throwable != null) {
                        log.info("任务存在异常，请注意！！");
                    }
                    log.info("{}\t保数据库", unused);
                }, executor);
        log.info("main 线程开始从 futures 中顺序获取获取集合子任务的结果");
        futuresCollection.forEach(task -> {
            try {
                log.info("get result = {}", task.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        TimeUnit.SECONDS.sleep(2);
        executor.shutdown();
    }
}
