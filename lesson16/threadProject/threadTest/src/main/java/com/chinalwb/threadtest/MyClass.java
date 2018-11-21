package com.chinalwb.threadtest;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("Hello");
        test();
    }

    private static void test() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread name ==  " + Thread.currentThread().getName());
            }
        });
        thread.start();
        Thread.State state = thread.getState();
        System.out.println("state == " + state.toString());
        thread.start();
    }
}
