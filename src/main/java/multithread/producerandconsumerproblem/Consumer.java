package multithread.producerandconsumerproblem;

import java.util.List;

public class Consumer implements Runnable {

    private int number;

    private List<String> breads;

    public Consumer(int number, List<String> breads) {
        this.number = number;
        this.breads = breads;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (breads) {
                if (breads.size() == 0) {
                    try {
                        breads.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("consumer" + number + " consume the number " + (breads.size() - 1) + " bread");
                    breads.remove(breads.size() - 1);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                breads.notifyAll();
            }
        }
    }
}
