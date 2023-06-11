package com.xjdl.study.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MyUtils {
    /**
     * 获取SpringBoot项目资源路径
     *
     * @param path 复制文件路径
     * @return 获取资源路径
     */
    public static String getResourcePath(String path) {
        String result = null;
        try {
            result = MyUtils.class.getClassLoader().getResource(path).getPath();
        } catch (NullPointerException e) {
            log.error("{} 路径对应的文件不存在", path);
        }
        return result;
    }

    /**
     * 获取当前线程
     *
     * @return 当前线程名称
     */
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 日志输出线程名称
     */
    public static void logThreadName() {
        log.info("当前线程是 {}", getThreadName());
    }
}
