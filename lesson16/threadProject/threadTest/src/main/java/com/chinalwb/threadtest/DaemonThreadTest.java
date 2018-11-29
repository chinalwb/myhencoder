package com.chinalwb.threadtest;

public class DaemonThreadTest {

    public static void main(String[] args) {
        System.out.println("Begin: I am in: " + Thread.currentThread().getName());
        ChildThread childThread = new ChildThread(100);
        childThread.setDaemon(true); // 设定 childThread 为守护线程。当其他非守护线程都结束之后 他就自动结束
        childThread.setName("C1");
        childThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("I am start C2...");
        ChildThread c2 = new ChildThread(5);
        c2.setName("C2");
        c2.start();
//        c2.setDaemon(true);

        // main线程结束。但C2没有结束。所以守护线程C1也不会自动结束。但是当C2结束之后, 守护线程C1自动结束
        System.out.println("End: I am in: " + Thread.currentThread().getName());
    }

    static class ChildThread extends Thread {
        int c;
        ChildThread(int c) {
            this.c = c;
        }
        @Override
        public void run() {
            int i = 0;
            while (i < this.c) {
                i++;
                System.out.println(Thread.currentThread().getName() + ":: I am in Child thread " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
