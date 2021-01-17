package multithread.threadpool.fourtypethreadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 单线程执行器
 * 每次执行一个任务就新建一个线程,这些线程以串行的方式执行
 * <p>
 * 下列程序模拟提交10个普通任务 打印i的值 可以看出i的值是递增的,而不是交错打印
 */
public class SingleThreadExecutorDemo {

    public static int i;

    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();
        System.out.println(executor.getClass().getName());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("i:" + i);
                i++;
            }
        };
        for (int i = 0; i < 10; i++) {
            executor.execute(runnable);
        }
    }
}
  
