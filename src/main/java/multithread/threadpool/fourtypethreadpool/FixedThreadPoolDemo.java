package multithread.threadpool.fourtypethreadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 固定长度的线程池
 * 指定线程数目后,提交的所有任务都会使用这几个线程来执行,不会新建线程
 * 如果前面的线程都还没执行完毕,只能等待前面的线程执行结束后,后面的线程才能执行(下列程序构造死循环来模拟等待)
 * 下列程序模拟提交10个死循环任务 最终活跃线程数为3个 队列任务数为7个
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        };
        for (int i = 0; i < 10; i++) {
            executor.execute(runnable);
        }

        while(true) {
            System.out.print("活跃线程数:" + ((ThreadPoolExecutor) executor).getActiveCount());
            System.out.println(" 队列任务数:" + ((ThreadPoolExecutor) executor).getQueue().size());
        }

    }
}
