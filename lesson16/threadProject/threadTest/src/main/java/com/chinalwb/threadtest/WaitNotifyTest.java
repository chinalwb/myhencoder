package com.chinalwb.threadtest;

public class WaitNotifyTest {
    public static void main(String[] args) {
        final Object lock = new Object();

        Thread serviceThread = new Thread() {
            @Override
            public void run() {
                Service service = new Service();
                service.doWork(lock);
            }
        };
        serviceThread.start();

        Thread1 t1 = new Thread1(lock);
        t1.start();

        Thread2 t2 = new Thread2(lock);
        t2.start();
    }
}

class Thread1 extends Thread {
    private Object lock;
    Thread1(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (this.lock) {
                System.out.println("Start Thread1");
                lock.notify();
                Thread.sleep(10 * 1000);
                System.out.println("End Thread1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread2 extends Thread {
    private Object lock;
    Thread2(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (this.lock) {
                System.out.println("Start Thread2");
                lock.notify();
                Thread.sleep(10 * 1000);
                System.out.println("End Thread2");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Service {
    public void doWork(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("ThreadName: " + Thread.currentThread().getName() + ", Begin service work!");
                System.out.println("I am lock waiting now.");
                lock.wait();
                System.out.println("ThreadName: " + Thread.currentThread().getName() + ", End service work!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
