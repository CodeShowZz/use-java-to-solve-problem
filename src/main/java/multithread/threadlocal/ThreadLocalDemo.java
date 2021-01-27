package multithread.threadlocal;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author junlin_huang
 * @create 2021-01-20 下午10:53
 **/

public class ThreadLocalDemo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
           System.out.println("aa");
        reentrantLock.unlock();

    }

}