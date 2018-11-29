package com.chinalwb.threadtest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    private int a = 0;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public static void main(String[] args) {
        ReadWriteLock my = new ReadWriteLock();
        my.test();
    }

    private void test() {

        Thread readThread = new Thread() {
            @Override
            public void run() {
                ReadWriteLock.this.read();
            }
        };
        readThread.start();

        Thread writeThread = new Thread() {
            @Override
            public void run() {
                ReadWriteLock.this.write();
            }
        };
        writeThread.start();

//        Thread readThread = new Thread() {
//            @Override
//            public void run() {
//                ReadWriteLock.this.read();
//            }
//        };
//        readThread.start();
        System.out.println("Testing..");
    }

    private void read() {
        readLock.lock();
        try {
            System.out.println("start read a == " + a + " : " + System.currentTimeMillis());
            Thread.sleep(3000);
            System.out.println("read done a == " + a + " : " + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    private void write() {
        writeLock.lock();
        try {
            System.out.println("Start write!" + a + " : " + System.currentTimeMillis());
            Thread.sleep(3000);

            a++;
            System.out.println("Write done!" + a + " : " + System.currentTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

}
