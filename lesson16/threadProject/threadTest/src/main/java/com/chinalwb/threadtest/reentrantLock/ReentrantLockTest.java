package com.chinalwb.threadtest.reentrantLock;

import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static void main(String[] args) {
        final Service service = new Service();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service.set();
            }
        };


        Timer timer = new Timer();


        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            service.lock.lock();
            service.condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Lock is unlocked " + Thread.currentThread().getName());
            service.lock.unlock();
        }
    }
}

class Service {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void set() {
        try {
            lock.lock();
            System.out.println("Lock is locked " + Thread.currentThread().getName());
            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("Lock is unlocked " + Thread.currentThread().getName());
            lock.unlock();
        }
    }
}
