package com.concurrency.threadpool.fourtypethreadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 可缓存的线程池
 * 线程池中没有多余的线程来执行任务,则会新建线程,另外线程可以复用,当线程空闲太久时也会被回收,总结起来就是灵活伸缩
 * <p>
 * 下列程序模拟提交5个死循环任务,5个普通任务  可以看出队列中没有任务,活跃的线程数也不是和任务个数成正比关系
 */
public class CachedThreadPoolDemo {

    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        Runnable deadLoop = new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        for (int i = 0; i < 5; i++) {
            executor.execute(deadLoop);
            executor.execute(runnable);
            System.out.print("活跃线程数:" + ((ThreadPoolExecutor) executor).getActiveCount());
            System.out.println(" 队列任务数:" + ((ThreadPoolExecutor) executor).getQueue().size());
        }
    }
}
