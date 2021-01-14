package multithread.lock.interruptablelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock.lockInterruptibly() 除了获取锁功能 还带有响应中断的机制
 */
public class InterruptableLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();
        Thread.sleep(1000);
        MyThread thread2 = new MyThread();
        thread2.start();
        Thread.sleep(1000);
        thread2.interrupt();
    }
}

class MyThread extends Thread {
    public static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        boolean lockInterruptiblyFlag = false;
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "尝试获取锁");
            lock.lockInterruptibly();
            System.out.println("线程" + Thread.currentThread().getName() + "获取到锁");
            while (true) {

            }
        } catch (InterruptedException e) {
            System.out.println("线程" + Thread.currentThread().getName() + "一直在等待锁 中断这个操作");
            lockInterruptiblyFlag = true;
        } finally {
            /**
             *   lock.lockInterruptibly()的弊端, 因为此时没有获取到锁, 调用unlock方法会报IllegalMonitorStateException
             *   此时可以用一个标志位来避免调用unlock方法
             */
            if (!lockInterruptiblyFlag) {
                lock.unlock();
            }
        }
    }
}
