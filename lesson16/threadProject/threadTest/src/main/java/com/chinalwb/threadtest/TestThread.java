package com.chinalwb.threadtest;

public class TestThread {
    public static void main(String[] args) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread.currentThread() == " + Thread.currentThread().getName());
                System.out.println("this.currentThread() == " + this.currentThread().getName());
            }
        };
        thread.start();
    }


}
