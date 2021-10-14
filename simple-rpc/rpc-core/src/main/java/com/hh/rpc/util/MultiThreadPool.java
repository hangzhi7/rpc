package com.hh.rpc.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 多线程配置
 *
 * @author hang.yuan 2021/10/14 15:49
 */
public class MultiThreadPool {

    private MultiThreadPool() {
    }

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    public static ThreadPoolTaskExecutor getInstance() {
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
