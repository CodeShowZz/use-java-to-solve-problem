package multithread.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义工厂 + 自定义线程
 * 线程工厂是用来创建线程的,在使用线程池的时候,传入一个线程工厂,让线程池知道该如何创建线程
 */
public class CustomThreadFactory {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new MyThreadFactory("MyThreadPool"), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread");
            }
        });
        Thread.sleep(1000);
        System.out.println(MyAppThread.getThreadsCreated());
        System.out.println(MyAppThread.getThreadsAlive());
    }

}


class MyThreadFactory implements ThreadFactory {

    private String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r);
    }
}

class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();

    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {

            }
        });
    }

    @Override
    public void run() {
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

}
