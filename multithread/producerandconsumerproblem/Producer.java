package com.concurrency.producerandconsumerproblem;

import java.util.List;

public class Producer implements Runnable {

    private int number;

    private List<String> breads;

    public Producer(int number, List<String> breads) {
        this.number = number;
        this.breads = breads;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (breads) {
                if (breads.size() == BreadConstant.MAX_BREAD_COUNT) {
                    try {
                        breads.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    breads.add("bread");
                    System.out.println("producer" + number + " produce the number " + (breads.size() - 1) + " bread");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                breads.notifyAll();
            }
        }
    }
}



