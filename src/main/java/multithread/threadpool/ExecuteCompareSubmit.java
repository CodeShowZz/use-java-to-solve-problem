package multithread.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * execute和submit方法的比较
 */
public class ExecuteCompareSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        //直接抛出异常
        threadPoolExecutor.execute(new Task());
        //这一步不会抛出异常,异常被封装在Future中
        Future future = threadPoolExecutor.submit(new Task());
        future.get();
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        int i = 1 / 0;
    }
}
