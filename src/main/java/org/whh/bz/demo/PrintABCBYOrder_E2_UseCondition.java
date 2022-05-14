package org.whh.bz.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCBYOrder_E2_UseCondition {
    static int mark = 4;
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition D = lock.newCondition();
        Condition E = lock.newCondition();
        Condition F = lock.newCondition();
        new Thread(()->{
            for (int i = 0; i < 2; i++) {
                lock.lock();
                    try {
                        while (mark!=4)
                        D.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                System.out.println("D");
                mark=5;
                E.signal();
                lock.unlock();
            }
        }).start();

        new Thread(()->{

            for (int i = 0; i < 2; i++) {
                lock.lock();
                    try {
                        while (mark!=5)
                        E.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                System.out.println("E");
                mark=6;
                F.signal();
                lock.unlock();
            }
        }).start();

        new Thread(()->{

            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    while (mark != 6)
                        F.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("F");
                mark = 4;
                D.signal();
                lock.unlock();
            }
        }).start();
    }
}

