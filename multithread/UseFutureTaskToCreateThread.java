package com.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的第三种方式
 * 前两种为1 继承Thread类 2 实现Runnable接口
 * 使用这种方式来创建线程 可以获得线程执行后的结果
 */
public class UseFutureTaskToCreateThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask(() ->
             "hello"
        );
        Thread thread = new Thread(futureTask);
        thread.start();
        String value = futureTask.get();
        System.out.println(value);
    }
}
