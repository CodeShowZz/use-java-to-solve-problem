package multithread.producerandconsumerproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * 多生产者多消费者问题
 */
public class Test {

    public static void main(String[] args) {
        List<String> breads = new ArrayList<>();
        Producer producer = new Producer(1,breads);
        Producer producer2 = new Producer(2,breads);
        Producer producer3 = new Producer(3,breads);
        Consumer consumer = new Consumer(1,breads);
        Consumer consumer2 = new Consumer(2,breads);
        Consumer consumer3 = new Consumer(3,breads);

        Thread producerThread = new Thread(producer);
        Thread producerThread2 = new Thread(producer2);
        Thread producerThread3 = new Thread(producer3);
        Thread consumerThread = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer2);
        Thread consumerThread3 = new Thread(consumer3);

        producerThread.start();
        producerThread2.start();
        producerThread3.start();
        consumerThread.start();
        consumerThread2.start();
        consumerThread3.start();
    }
}
