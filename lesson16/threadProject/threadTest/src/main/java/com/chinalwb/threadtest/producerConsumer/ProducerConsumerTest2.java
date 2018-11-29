package com.chinalwb.threadtest.producerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerTest2 {
    public static void main(String[] args) {
        BufferArea bufferArea = new BufferArea();
        ProducerThread producerThread = new ProducerThread(bufferArea);
        ConsumerThread consumerThread = new ConsumerThread(bufferArea);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProducerThread extends Thread {
    BufferArea bufferArea;

    public ProducerThread(BufferArea bufferArea) {
        this.bufferArea = bufferArea;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            int v = new Random().nextInt(100);
            bufferArea.put(v);
        }
    }
}

class ConsumerThread extends Thread {
    BufferArea bufferArea;

    public ConsumerThread(BufferArea bufferArea) {
        this.bufferArea = bufferArea;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            bufferArea.remove();
        }
    }
}

class BufferArea {
    private static final int CAPACITY = 10;
    private List<Integer> elements = new ArrayList<>();

    synchronized public void put(int value) {
        try {
            while (elements.size() == CAPACITY) {
                System.out.println("Add Waiting .." + Thread.currentThread().getName());
                this.wait();
            }

            Thread.sleep(100);
            boolean added = elements.add(value);
            if (added) {
                System.out.println("Added: " + value);
            }
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void remove() {
        try {
            while (elements.size() == 0) {
                System.out.println("Remove Waiting .. " + Thread.currentThread().getName());
                this.wait();
            }

            Thread.sleep(2000);
            int value = elements.remove(0);
            System.out.println("Removed: " + value);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
