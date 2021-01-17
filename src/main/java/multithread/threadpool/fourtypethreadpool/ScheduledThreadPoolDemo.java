package multithread.threadpool.fourtypethreadpool;

import java.util.concurrent.*;

/**
 * 相当于带定时功能的FixedThreadPool
 */
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        };
        for (int i = 0; i < 10; i++) {
            executor.schedule(runnable, 1, TimeUnit.SECONDS);
        }

        while (true) {
            System.out.print("活跃线程数:" + (executor).getActiveCount());
            System.out.println(" 队列任务数:" + (executor).getQueue().size());
        }

    }
}
