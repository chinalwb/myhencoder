package com.chinalwb.threadtest.producerConsumer.concurrentLinkedQueue;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerConsumerCLQ {
    public static void main(String[] args) {
        Products products = new Products();
        ProduceThread produceThread = new ProduceThread(products);
        ConsumeThread consumeThread = new ConsumeThread(products);
        ConsumeThread consumeThread2 = new ConsumeThread(products);
        ConsumeThread consumeThread3 = new ConsumeThread(products);
        ConsumeThread consumeThread4 = new ConsumeThread(products);
        produceThread.start();
        consumeThread.start();
        consumeThread2.start();
        consumeThread3.start();
        consumeThread4.start();
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
            Thread.interrupted();
            int v = this.products.produce();
            System.out.println("++ Produced == " + v + ", size == " + this.products.size() + ", t = " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConsumeThread extends Thread {
    private Products products;
    ConsumeThread(Products products) {
        this.products = products;
    }
    @Override
    public void run() {
        while (true) {
            int c = this.products.consume();
            System.out.println("-- Consumed == " + c + ", size == " + this.products.size() + ", t = " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Products {

    private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

    public int produce() {
        int v = new Random().nextInt(20);
        boolean added = queue.add(v);
        if (added) {
            return v;
        }
        return -1;
    }

    public int consume() {
        int v = queue.peek() == null ? -1 : queue.poll();
        return v;
    }

    public int size() {
        return queue.size();
    }
}
