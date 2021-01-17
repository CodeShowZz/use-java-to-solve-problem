package multithread.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock可以绑定多个Condition 获取锁的线程可以选择在某个Condition上进行等待/唤醒,以此来实现指定线程的唤醒
 * 该程序演示如何使用Condition来使三个 轮流打印数字
 **/
public class UseConditionToRotatePrintNumber {

    public static void main(String[] args) {
        new Thread(new PrintNumberTask("A")).start();
        new Thread(new PrintNumberTask("B")).start();
        new Thread(new PrintNumberTask("C")).start();
    }
}

class PrintNumberTask implements Runnable {

    public static int number = 1;

    public static Lock lock = new ReentrantLock();

    public static Condition condition1 = lock.newCondition();

    public static Condition condition2 = lock.newCondition();

    public static Condition condition3 = lock.newCondition();

    public String threadName;

    public PrintNumberTask(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if ("A".equals(threadName) && number == 1) {
                    System.out.println(threadName + ":" + number);
                    number++;
                    condition2.signal();
                    condition1.await();
                } else if ("B".equals(threadName) && number == 2) {
                    System.out.println(threadName + ":" + number);
                    number++;
                    condition3.signal();
                    condition2.await();
                } else if ("C".equals(threadName) && number == 3) {
                    System.out.println(threadName + ":" + number);
                    number = 1;
                    condition1.signal();
                    condition3.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
