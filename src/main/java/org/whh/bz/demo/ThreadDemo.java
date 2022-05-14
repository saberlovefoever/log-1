package org.whh.bz.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread1 = new Thread(()->System.out.println(Thread.currentThread()+"!"));

        ExecutorService executor = new ThreadPoolExecutor(3,5,3000, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
        executor.execute(thread1);
    }
}
