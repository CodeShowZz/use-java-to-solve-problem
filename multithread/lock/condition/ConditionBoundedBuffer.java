package multithread.lock.condition;

import multithread.annotation.ThreadSafe;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition模拟生产者消费者问题  操作对象是一个固定长度的数组,并加入了循环存取特性
 *
 * @param <T>
 */
@ThreadSafe
public class ConditionBoundedBuffer<T> {

    protected final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private static final int BUFFER_SIZE = 15;
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail, head, count;

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public void print() {
        while (true) {
            lock.lock();
            try {
                System.out.println(Arrays.toString(items));
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ConditionBoundedBuffer<Integer> buffer = new ConditionBoundedBuffer<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (true) {
                    try {
                        buffer.put(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    if (i == 10) {
                        i = 0;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        buffer.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                buffer.print();
            }
        }).start();
    }
}
