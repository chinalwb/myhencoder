package com.chinalwb.threadtest.producerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Products products = new Products();
        Producer producer = new Producer(products, lock);
        Consumer consumer = new Consumer(products, lock);

        PThread pThread = new PThread(producer);
        CThread cThread1 = new CThread(consumer);
        CThread cThread2 = new CThread(consumer);
        pThread.start();
        cThread1.start();
        cThread2.start();
    }
}

class PThread extends Thread {
    private Producer producer;
    public PThread(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            this.producer.produce();
        }
    }
}

class CThread extends Thread {
    private Consumer consumer;
    public CThread(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            this.consumer.consume();
        }
    }
}

class Producer {

    private Object lock;
    private Products products;
    public Producer(Products products, Object lock) {
        this.products = products;
        this.lock = lock;
    }

    public int produce() {
        try {
            synchronized (lock) {
                if (products.size() == 10) {
                    lock.wait();
                }
                System.out.println("Producing...");
                Thread.sleep(1000);
                int v = products.size() + 1;
                int result = products.add(v);
                System.out.println("Produced = " + result);
                lock.notifyAll();
                return result;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  -1;
        }
    }
}

class Consumer {
    private Object lock;
    private Products products;
    public Consumer(Products products, Object lock) {
        this.products = products;
        this.lock = lock;
    }

    public int consume() {
        try {
            synchronized (this.lock) {
                if (this.products.size() == 0) {
                    lock.wait();
                }

                System.out.println("Consuming...");
                Thread.sleep(2000);
                int result = this.products.remove();
                System.out.println("Consumed = " + result);
                lock.notifyAll();
                return result;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

class Products {
    List<Integer> productsList = new ArrayList<>();

    public int add(int product) {
        boolean addResult = productsList.add(product);
        if (addResult) {
            return product;
        }
        return -1;
    }

    public int remove() {
        return productsList.remove(0);
    }

    public int size() {
        return productsList.size();
    }
}