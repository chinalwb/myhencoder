package com.chinalwb.threadtest.producerConsumer.lock;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerLockTest {

    public static void main(String[] args) {
        Products products = new Products();
        ProduceThread produceThread = new ProduceThread(products);
        ConsumeThread consumeThread = new ConsumeThread(products);
        produceThread.start();
        consumeThread.start();
    }


}

class ProduceThread extends Thread {
    Products products;
    ProduceThread(Products products) {
        this.products = products;
    }

    @Override
    public void run() {
        while (true) {
            products.produce();
        }
    }
}

class ConsumeThread extends Thread {

    Products products;
    ConsumeThread(Products products) {
        this.products = products;
    }

    @Override
    public void run() {
        while (true) {
            products.consume();
        }
    }
}

class Products {
    private Lock lock = new ReentrantLock();
    private Condition isFullCondition = lock.newCondition();
    private Condition isEmptyCondition = lock.newCondition();
    private List<Integer> productsList = new ArrayList<>();
    private static final int MAX_CAPACITY = 10;
    public void produce() {
        try {
            lock.lock();
            while (productsList.size() == MAX_CAPACITY) {
                isFullCondition.await();
            }
            int v = new Random().nextInt(20);
            productsList.add(v);
            Thread.sleep(1000);
            System.out.println("++ Produced == " + v + ", size = " + this.productsList.size() + ", t = " + Thread.currentThread().getName());
            isEmptyCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            Thread.yield();
        }
    }

    public void consume() {
        try {
            lock.lock();
            while (productsList.size() == 0) {
                isEmptyCondition.await();
            }

            Thread.sleep(2000);
            int v = productsList.remove(0);
            System.out.println("-- Consumed == " + v + ", size = " + this.productsList.size() + ", t = " + Thread.currentThread().getName());
            isFullCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            Thread.yield();
        }
    }

}
