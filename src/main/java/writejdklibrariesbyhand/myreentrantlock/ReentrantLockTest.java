package writejdklibrariesbyhand.myreentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 初始化三个线程 每个线程都不断的使用锁,释放锁,当获得锁的时候 打印自己的线程号 观察输出的结果来验证
 * 我们这个简单的AQS的正确性,为了使打印更慢一点,线程将会睡眠1秒
 *
 * @author junlin_huang
 * @create 2021-01-28 上午1:37
 **/

public class ReentrantLockTest {


    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "获得了锁");
                System.out.println(threadName + "释放了锁");
                reentrantLock.unlock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "获得了锁");
                System.out.println(threadName + "释放了锁");
                reentrantLock.unlock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "获得了锁");
                System.out.println(threadName + "释放了锁");
                reentrantLock.unlock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}