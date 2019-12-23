package com.cosun.cosunp.tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:homey Wong
 * @date:2019/6/11 0011 上午 9:44
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ThreadUtil {


    public static ExecutorService buildDownloadBatchThreadPool(int threadSize) {
        int keepAlive = 0;
        String prefix = "download-batch";
        ThreadFactory factory = ThreadUtil.buildThreadFactory(prefix);
        return new ThreadPoolExecutor(threadSize, threadSize, keepAlive, TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadSize), factory);
    }

    public static ThreadFactory buildThreadFactory(String prefix) {
        return new CustomThreadFactory(prefix);
    }


    public static class CustomThreadFactory implements ThreadFactory {
        private String threadNamePrefix;
        private AtomicInteger counter = new AtomicInteger(1);


        CustomThreadFactory(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName = threadNamePrefix + "-t" + counter.getAndIncrement();
            return new Thread(r, threadName);
        }
    }




}
